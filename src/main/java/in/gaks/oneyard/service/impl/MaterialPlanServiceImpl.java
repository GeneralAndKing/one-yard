package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.PlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.Notification;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.MaterialPlanService;
import in.gaks.oneyard.service.MaterialPlanSummaryService;
import in.gaks.oneyard.service.PlanMaterialService;
import in.gaks.oneyard.service.ProcurementPlanService;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.List;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author Japoul
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class MaterialPlanServiceImpl extends BaseServiceImpl<MaterialDemandPlanRepository,
    MaterialDemandPlan, Long>
    implements MaterialPlanService {

  private final @NonNull MaterialDemandPlanRepository materialPlanRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;
  private final @NonNull ApprovalRepository approvalRepository;
  private final @NonNull NotificationRepository notificationRepository;
  private final @NonNull SysUserRepository sysUserRepository;
  private final @NonNull MaterialPlanSummaryService materialPlanSummaryService;
  private final @NonNull PlanMaterialService planMaterialService;
  private final @NonNull ProcurementPlanService procurementPlanService;
  private final @NonNull NotifyUtil notifyUtil;

  /**
   * 保存/修改物料需求计划表.
   *
   * @param materialPlan 物料需求计划基础信息
   * @param materials    需求的物资列表n
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void savePlanAndPlanMaterials(MaterialDemandPlan materialPlan,
      List<PlanMaterial> materials) {
    materialPlanRepository.save(materialPlan);
    Long planId = materialPlan.getId();
    //循环保存
    materials.forEach(material -> material.setPlanId(planId));
    planMaterialRepository.saveAll(materials);
  }

  /**
   * 根据id获取完整的需求计划表.
   *
   * @param id 计划表id
   * @return 完整的计划表
   */
  @Override
  public MaterialDemandPlan findByIdToMaterial(Long id) {
    MaterialDemandPlan plan = materialPlanRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("需求计划查询失败"));
    plan.setMaterials(planMaterialService.findAllByPlanId(id, true));
    return plan;
  }

  /**
   * 主管审批需求物料计划.
   *
   * @param materialDemandPlan 需求计划
   * @param approval           审批信息
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void approvalMaterialPlan(MaterialDemandPlan materialDemandPlan, Approval approval) {
    // 保存审批信息，回溯流程
    approvalRepository.save(approval);
    // 发送通知并存入数据库
    Notification notification = new Notification();
    if ("部门主管审批通过".equals(approval.getResult())) {
      // 更新计划审核状态并根据时间自动汇总
      List<PlanMaterial> planMaterials = planMaterialRepository
          .findAllByPlanId(materialDemandPlan.getId());
      // 紧急计划自动进入紧急采购计划
      if ("紧急计划".equals(materialDemandPlan.getPlanType())) {
        Long procurementPlanId = procurementPlanService
            .getUrgentProcurementId(materialDemandPlan.getName());
        planMaterials.forEach(planMaterial ->
            planMaterial.setProcurementPlanId(procurementPlanId).setSupplyMode("采购"));
      }
      planMaterials.forEach(planMaterial -> planMaterial
          .setSummaryId(materialPlanSummaryService.summaryMaterialPlan(materialDemandPlan)));
      planMaterialRepository.saveAll(planMaterials);
      notification.setMessage("您于" + materialDemandPlan.getCreateTime()
          + "提报创建的 " + materialDemandPlan.getName() + " 由部门主管审批通过了！")
          .setName("需求计划审批通过通知");
    } else {
      notification.setMessage("您于" + materialDemandPlan.getCreateTime()
          + "提报创建的需求计划 《" + materialDemandPlan.getName() + " 》因为某些原因被主管退回了。")
          .setName("需求计划审批退回通知");
    }
    //保存需求计划表
    materialPlanRepository.save(materialDemandPlan);

    // 获取通知接收方id
    SysUser user = sysUserRepository.findFirstByUsername(materialDemandPlan.getCreateUser())
        .orElseThrow(() -> new ResourceNotFoundException("该计划的提报员查询失败"));
    notification.setReceiverId(user.getId());
    notificationRepository.save(notification);
    // 检测用户是否在线发送通知
    notifyUtil.sendMessage(user.getId().toString(), notification);
  }

  /**
   * 撤回审批.
   *
   * @param id 需求计划id
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void withdrawApproval(Long id) {
    MaterialDemandPlan plan = materialPlanRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("需求计划查询失败"));
    if (!ApprovalStatus.APPROVAL_ING.equals(plan.getApprovalStatus())
        || !PlanStatus.APPROVAL.equals(plan.getPlanStatus())) {
      throw new ResourceErrorException("当前项目状态有误，刷新后再试！");
    }
    plan.setPlanStatus(PlanStatus.FREE).setApprovalStatus(ApprovalStatus.NO_SUBMIT);
    materialPlanRepository.save(plan);
  }
}
