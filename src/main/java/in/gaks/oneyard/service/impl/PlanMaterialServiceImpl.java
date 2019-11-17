package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.ApprovalTypeStatus;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.constant.NotificationStatus;
import in.gaks.oneyard.model.constant.PlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.Material;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialType;
import in.gaks.oneyard.model.entity.Notification;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialRepository;
import in.gaks.oneyard.repository.MaterialTypeRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementPlanRepository;
import in.gaks.oneyard.repository.SysDepartmentRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.PlanMaterialService;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author Japoul
 * @date 2019/11/11 下午11:01
 */
@Service
@RequiredArgsConstructor
public class PlanMaterialServiceImpl extends BaseServiceImpl<PlanMaterialRepository,
    PlanMaterial, Long>
    implements PlanMaterialService {

  private final @NonNull MaterialRepository materialRepository;
  private final @NonNull MaterialTypeRepository materialTypeRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;
  private final @NonNull ProcurementPlanRepository procurementPlanRepository;
  private final @NonNull MaterialDemandPlanRepository materialPlanRepository;
  private final @NonNull SysDepartmentRepository sysDepartmentRepository;
  private final @NonNull ApprovalRepository approvalRepository;
  private final @NonNull SysUserRepository sysUserRepository;
  private final @NonNull NotificationRepository notificationRepository;
  private final NotifyUtil notifyUtil;

  /**
   * 根据需求计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   */
  @Override
  public List<PlanMaterial> findAllByPlanId(Long id) {
    List<Long> procurementIds = new ArrayList<>();
    procurementPlanRepository
        .findAllByPlanStatusAndApprovalStatus(PlanStatus.FINALLY, ApprovalStatus.APPROVAL_OK)
        .forEach(p -> {
          procurementIds.add(p.getId());
        });
    return planMaterialRepository
        .findAllByPlanIdAndStatusAndProcurementPlanIdIsNull(id, MaterialStatus.INIT)
        .stream().peek(planMaterial -> {
          Long inTransitNum = 0L;
          Long occupiedNum = 0L;

          planMaterial.setDepartmentName(getDepartmentNameByPlanId(planMaterial.getPlanId()));
          Material material = materialRepository.findById(planMaterial.getMaterialId()).orElseThrow(
              () -> new ResourceNotFoundException("物料主数据查询失败"));
          planMaterial.setMaterial(material);
          MaterialType materialType = materialTypeRepository
              .findById(planMaterial.getMaterialTypeId()).orElseThrow(
                  () -> new ResourceNotFoundException("物料类别主数据查询失败"));
          planMaterial.setMaterialType(materialType);

          //若没有在正在进行的采购计划则表示该物资已占库存数为0
          if (procurementIds.size() != 0) {
            //获取已占库存
            List<Long> nums = planMaterialRepository
                .searchByProcurementPlanIdsAndSupplyMode(procurementIds,
                    planMaterial.getMaterialId(), "采购");
            if (Objects.nonNull(nums)) {
              for (Long n : nums) {
                occupiedNum += n;
              }
            }
          }

          //若没有在正在进行的采购计划则表示在途数量为0
          if (procurementIds.size() != 0) {
            //获取在途数量
            List<Long> nums = planMaterialRepository
                .searchByProcurementPlanIdsAndSupplyMode(procurementIds,
                    planMaterial.getMaterialId(), "采购");
            if (Objects.nonNull(nums)) {
              for (Long n : nums) {
                inTransitNum += n;
              }
            }
          }
          planMaterial.setAvailableNum(planMaterial.getNumber() - occupiedNum + inTransitNum);
          planMaterial.setDepartmentName(getDepartmentNameByPlanId(planMaterial.getPlanId()));
          planMaterial.setOccupiedNum(occupiedNum);
          planMaterial.setInTransitNum(inTransitNum);
        }).collect(Collectors.toList());
  }

  /**
   * 根据采购计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   */
  @Override
  @Deprecated
  public List<PlanMaterial> findAllByProcurementPlanId(Long id) {
    return planMaterialRepository.findAllByProcurementPlanIdAndStatus(id, MaterialStatus.INIT);
  }

  /**
   * 根据需求计划id查询所需部门.
   *
   * @param planId 需求计划id
   */
  @Override
  public String getDepartmentNameByPlanId(Long planId) {
    Long departmentId = materialPlanRepository.findById(planId)
        .orElseThrow(() -> new ResourceNotFoundException("需求计划查询失败")).getDepartmentId();
    return sysDepartmentRepository.findById(departmentId)
        .orElseThrow(() -> new ResourceNotFoundException("需求部门查询失败")).getName();
  }

  /**
   * 退回需求.
   *
   * @param flag true：整个计划，false：当前物资
   * @param planMaterial0 需求物资
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void backPlanOrMaterial(boolean flag, PlanMaterial planMaterial0, Approval approve) {
    //统一对象
    PlanMaterial planMaterial = planMaterialRepository.findById(planMaterial0.getId())
        .orElseThrow(() -> new ResourceNotFoundException("找不到对应物资，可能已被删除！"));
    Notification notification = new Notification();
    MaterialDemandPlan materialPlan = materialPlanRepository.findById(planMaterial.getPlanId())
        .orElseThrow(() -> new ResourceNotFoundException("需求计划查询失败"));
    if (!flag) {
      //设置退回状态
      planMaterial.setPlanId(null);
      planMaterial.setStatus(MaterialStatus.BACK);
      planMaterialRepository.save(planMaterial);

      //获取对应的物料数据发送通知
      Material material = materialRepository.findById(planMaterial.getMaterialId()).orElseThrow(
          () -> new ResourceNotFoundException("物料主数据查询失败"));
      planMaterial.setMaterial(material);
      MaterialType materialType = materialTypeRepository
          .findById(planMaterial.getMaterialTypeId()).orElseThrow(
              () -> new ResourceNotFoundException("物料类别主数据查询失败"));

      approve.setResult("采购部门退回一条物资需求");

      //设置通知参数
      notification.setName("需求物资退回通知");
      notification.setMessage("您于《" + materialPlan.getName()
          + "》中提报的需求物资被采购部门退回了，请重新草拟计划提报。物资部分详细信息为："
          + "<br/>物资类别及编号：[" + materialType.getCode() + "]" + materialType.getName()
          + "<br/>物料名称及编号：["
          + material.getCode() + "]" + material.getName() + "<br/>需求数量：" + planMaterial.getNumber()
      );
    } else {
      materialPlan.setSummaryId(null);
      materialPlan.setApprovalStatus(ApprovalStatus.APPROVAL_NO);
      materialPlan.setPlanStatus(PlanStatus.FREE);

      approve.setResult("采购部门审批退回");

      //设置通知参数
      notification.setName("需求物资退回通知");
      notification.setMessage("您于" + materialPlan.getCreateTime()
          + "提报创建的需求计划 《" + materialPlan.getName() + "》 因为某些原因被采购部门退回了。");
    }
    //保存退回信息至审批
    approve.setPlanId(planMaterial0.getPlanId());
    approve.setApprovalType(ApprovalTypeStatus.PROCUREMENT_APPROVAL_ONE);
    approvalRepository.save(approve);

    // 获取通知接收方id
    SysUser user = sysUserRepository.findFirstByUsername(planMaterial.getCreateUser())
        .orElseThrow(() -> new ResourceNotFoundException("该计划的提报员查询失败"));
    notification.setReceiverId(user.getId());
    notification.setStatus(NotificationStatus.UNREAD);
    notificationRepository.save(notification);
    // 检测用户是否在线发送通知
    notifyUtil.sendMessage(user.getId().toString(), notification);
  }
}
