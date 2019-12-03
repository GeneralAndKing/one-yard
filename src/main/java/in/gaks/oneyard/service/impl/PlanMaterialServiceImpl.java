package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.*;
import in.gaks.oneyard.model.entity.*;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.*;
import in.gaks.oneyard.service.PlanMaterialService;
import in.gaks.oneyard.util.NotifyUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
  private final @NonNull NotifyUtil notifyUtil;

  /**
   * 根据需求计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @param type 调用该方法的类型 true 需求计划 false 汇总表
   * @return 完整的需求物资数据
   */
  @Override
  public List<PlanMaterial> findAllByPlanId(Long id, boolean type) {
    //获取物料库存管理准备
    List<Long> procurementIds = new ArrayList<>();
    procurementPlanRepository
        .findAllByPlanStatusAndApprovalStatus(PlanStatus.FINALLY, ApprovalStatus.APPROVAL_OK)
        .forEach(p -> procurementIds.add(p.getId()));
    List<PlanMaterial> pMaterials;
    if (type) {
      pMaterials = planMaterialRepository
          .findAllByPlanId(id);
    } else {
      pMaterials = planMaterialRepository
          .findAllBySummaryIdAndStatusAndProcurementPlanIdIsNull(id, MaterialStatus.INIT);
    }
    return pMaterials.stream().peek(planMaterial -> {
      Long inTransitNum = 0L;
      Long occupiedNum = 0L;

      Material material = materialRepository.findById(planMaterial.getMaterialId()).orElseThrow(
          () -> new ResourceNotFoundException("物料主数据查询失败"));
      planMaterial.setMaterial(material);
      MaterialType materialType = materialTypeRepository
          .findById(planMaterial.getMaterialTypeId()).orElseThrow(
              () -> new ResourceNotFoundException("物料类别主数据查询失败"));
      planMaterial.setMaterialType(materialType);

      //获取已占库存
      List<PlanMaterial> planMaterials = planMaterialRepository
          .findAllBySupplyModeAndStatus("库存供应", MaterialStatus.INIT);
      if (Objects.nonNull(planMaterials)) {
        for (PlanMaterial p : planMaterials) {
          occupiedNum += p.getNumber();
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
      planMaterial.setDepartmentName(null);
      if (Objects.nonNull(planMaterial.getPlanId())) {
        planMaterial.setDepartmentName(getDepartmentNameByPlanId(planMaterial.getPlanId()));
      }
      planMaterial.setOccupiedNum(occupiedNum);
      planMaterial.setInTransitNum(inTransitNum);

      //设置可用库存
      planMaterial.setAvailableNum(
          material.getNumber() - occupiedNum - material.getLowNumber() + inTransitNum);
    }).collect(Collectors.toList());
  }

  /**
   * 根据采购计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   * @since 1.0.0
   * @deprecated 因某些问题而被废弃
   */
  @Override
  @Deprecated(since = "1.0.0")
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
   * @param approve 审批意见
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
      //从汇总表中删除
      List<PlanMaterial> planMaterials = planMaterialRepository
          .findAllByPlanId(materialPlan.getId());
      planMaterials.forEach(planMaterial1 -> planMaterial1.setSummaryId(null));
      planMaterialRepository.saveAll(planMaterials);
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
    approve.setApprovalType(ApprovalTypeStatus.PROCUREMENT_APPROVAL);
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

  /**
   * 合并物料.
   *
   * @param planMaterial 合并后的物料
   * @param ids 待合并的ids
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public PlanMaterial mergeMaterialPlan(PlanMaterial planMaterial, List<Long> ids) {

    List<PlanMaterial> all = planMaterialRepository.findAllById(ids);
    for (PlanMaterial item : all) {
      item.setStatus(MaterialStatus.MERGE);
      item.setProcurementPlanId(null);
    }
    planMaterialRepository.saveAll(all);
    return planMaterialRepository.save(planMaterial);
  }

  /**
   * 拆分物料.
   *
   * @param planMaterial 被拆分的物料
   * @param newPlanMaterials 拆分成的物料列表
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public List<PlanMaterial> splitMaterialPlan(PlanMaterial planMaterial,
      List<PlanMaterial> newPlanMaterials) {
    if (Objects.isNull(planMaterial.getId())) {
      throw new ResourceErrorException("被拆分的物料id不能为空！");
    }
    PlanMaterial pm = planMaterialRepository.findById(planMaterial.getId())
        .orElseThrow(() -> new ResourceNotFoundException("未找到指定需求物料数据！"));
    if (Objects.nonNull(pm.getProcurementPlanId()) || !pm.getStatus().equals(MaterialStatus.INIT)) {
      throw new ResourceErrorException("数据可能已经被更改，请刷新后再试！");
    }
    pm.setStatus(MaterialStatus.SPLIT);
    pm.setIsEnable(false);
    newPlanMaterials.add(0, pm);
    return planMaterialRepository.saveAll(newPlanMaterials);
  }
}
