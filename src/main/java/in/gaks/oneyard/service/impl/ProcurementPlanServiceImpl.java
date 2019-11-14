package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.ApprovalTypeStatus;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import in.gaks.oneyard.model.entity.Notification;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialPlanSummaryRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementPlanRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.MaterialPlanSummaryService;
import in.gaks.oneyard.service.PlanMaterialService;
import in.gaks.oneyard.service.ProcurementPlanService;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 汇总表事务处理.
 *
 * @author Japoul
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class ProcurementPlanServiceImpl extends BaseServiceImpl<ProcurementPlanRepository,
    ProcurementPlan, Long>
    implements ProcurementPlanService {

  private final @NonNull ProcurementPlanRepository procurementPlanRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;
  private final @NonNull ApprovalRepository approvalRepository;
  private final @NonNull SysUserRepository sysUserRepository;
  private final @NonNull NotificationRepository notificationRepository;
  private final NotifyUtil notifyUtil;

  /**
   * 根据id获取完整的需求计划表.
   *
   * @param id 汇总表id
   * @return 完整的汇总表
   */
  @Override
  public ProcurementPlan findByIdToMaterials(Long id) {
    ProcurementPlan plan = procurementPlanRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("需求汇总表查询失败"));
    List<PlanMaterial> materials = planMaterialRepository
        .findAllByProcurementPlanIdAndStatus(id, MaterialStatus.INIT);
    plan.setPlanMaterials(materials);
    return plan;
  }

  /**
   * 采购主管/财务审批采购计划.
   *
   * @param procurementPlan 需求计划
   * @param approval 审批信息
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void approvalProcurementPlan(ProcurementPlan procurementPlan, Approval approval) {
    // 更新计划审核状态
    procurementPlanRepository.save(procurementPlan);
    // 保存审批信息，回溯流程
    approvalRepository.save(approval);
    // 发送通知并存入数据库
    Notification notification = new Notification();
    //根据审批环节和审批意见发送不同的通知信息
    String res = approval.getResult();
    ApprovalTypeStatus type = approval.getApprovalType();
    if ("审批通过".equals(res)
        && type.equals(ApprovalTypeStatus.PROCUREMENT_APPROVAL_ONE)) {
      notification.setName("采购计划主管审批通过通知");
      notification.setMessage("您于" + procurementPlan.getCreateTime()
          + "生成提交的采购计划《 " + procurementPlan.getName() + "》被主管审批通过了！");
    } else if ("审批退回".equals(res)
        && type.equals(ApprovalTypeStatus.PROCUREMENT_APPROVAL_ONE)) {
      notification.setName("采购计划主管审批退回通知");
      notification.setMessage("您于" + procurementPlan.getCreateTime()
          + "生成提交的采购计划《" + procurementPlan.getName() + " 》因为某些原因被主管退回了。");
    } else if ("审批通过".equals(res)
        && type.equals(ApprovalTypeStatus.PROCUREMENT_APPROVAL_TWO)) {
      notification.setName("采购计划财务审批通过通知");
      notification.setMessage("您于" + procurementPlan.getCreateTime()
          + "生成提交的采购计划《" + procurementPlan.getName() + " 》被财务部门审批通过了！");
    } else if ("审批退回".equals(res)
        && type.equals(ApprovalTypeStatus.PROCUREMENT_APPROVAL_TWO)) {
      notification.setName("采购计划财务审批退回通知");
      notification.setMessage("您于" + procurementPlan.getCreateTime()
          + "生成提交的采购计划《" + procurementPlan.getName() + " 》因为某些原因被财务部门退回了。");
    }
    // 获取通知接收方id
    SysUser user = sysUserRepository.findFirstByUsername(procurementPlan.getCreateUser())
        .orElseThrow(() -> new ResourceNotFoundException("采购计划员查询失败"));
    notification.setReceiverId(user.getId());
    notificationRepository.save(notification);
    // 检测用户是否在线发送通知
    notifyUtil.sendMessage(user.getId().toString(), notification);
  }
}
