package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.ApprovalTypeStatus;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.constant.PlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.Notification;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.entity.dto.ProcurementPlanDto;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementPlanRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.repository.dto.ProcurementPlanDtoRepository;
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
 * 采购计划表事务处理.
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
  private final @NonNull ProcurementPlanDtoRepository procurementPlanDtoRepository;
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
  public List<ProcurementPlanDto> findByIdToMaterials(Long id) {
    List<ProcurementPlanDto> plans = procurementPlanDtoRepository.searchInfoById(id);
    if (Objects.isNull(plans)) {
      throw new ResourceNotFoundException("找不到对应的数据！");
    }
    List<Long> procurementIds = new ArrayList<>();
    procurementPlanRepository
        .findAllByPlanStatusAndApprovalStatus(PlanStatus.FINALLY, ApprovalStatus.APPROVAL_OK)
        .forEach(p -> procurementIds.add(p.getId()));
    plans.forEach(plan -> {
      Long inTransitNum = 0L;
      Long occupiedNum = 0L;
      //若没有在正在进行的采购计划则表示在途数量为0
      if (procurementIds.size() != 0) {
        //获取在途数量
        List<Long> nums = planMaterialRepository
            .searchByProcurementPlanIdsAndSupplyMode(procurementIds, plan.getMaterialId(), "采购");
        if (Objects.nonNull(nums)) {
          for (Long n : nums) {
            inTransitNum += n;
          }
        }
      }
      plan.setInTransitNum(inTransitNum);

      //获取已占库存
      List<PlanMaterial> planMaterials = planMaterialRepository
          .findAllBySupplyModeAndStatus("库存供应", MaterialStatus.INIT);
      if (Objects.nonNull(planMaterials)) {
        for (PlanMaterial p : planMaterials) {
          occupiedNum += p.getNumber();
        }
      }
      plan.setOccupiedNum(occupiedNum);
      //设置可用库存
      plan.setAvailableNum(
          plan.getMaterialNumber() - occupiedNum + inTransitNum - plan.getMaterialLowNumber());

    });
    return plans;
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
    if ("采购主管审批通过".equals(res)) {
      notification.setName("采购计划主管审批通过通知");
      notification.setMessage("您于" + procurementPlan.getCreateTime()
          + "生成提交的采购计划《 " + procurementPlan.getName() + "》被主管审批通过了！");
    } else if ("采购主管审批退回".equals(res)) {
      notification.setName("采购计划主管审批退回通知");
      notification.setMessage("您于" + procurementPlan.getCreateTime()
          + "生成提交的采购计划《" + procurementPlan.getName() + " 》因为某些原因被主管退回了。");
    } else if ("财务审批通过".equals(res)) {
      notification.setName("采购计划财务审批通过通知");
      notification.setMessage("您于" + procurementPlan.getCreateTime()
          + "生成提交的采购计划《" + procurementPlan.getName() + " 》被财务部门审批通过了！");
    } else if ("财务审批退回".equals(res)) {
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

  /**
   * 撤回审批.
   *
   * @param procurementPlan 采购计划
   * @param role 角色类型
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void withdrawApproval(ProcurementPlan procurementPlan, String role) {
    if ("PLANER".equals(role) && procurementPlan.getPlanStatus().equals(PlanStatus.APPROVAL)
        && procurementPlan.getApprovalStatus().equals(
        ApprovalStatus.APPROVAL_ING)) {
      procurementPlan.setPlanStatus(PlanStatus.FREE).setApprovalStatus(ApprovalStatus.NO_SUBMIT);
      procurementPlanRepository.save(procurementPlan);
    } else if ("SUPERVISOR".equals(role) && procurementPlan.getPlanStatus()
        .equals(PlanStatus.PROCUREMENT_OK)
        && procurementPlan.getApprovalStatus().equals(
        ApprovalStatus.APPROVAL_ING)) {
      procurementPlan.setPlanStatus(PlanStatus.APPROVAL);
      procurementPlanRepository.save(procurementPlan);
      Approval approval = new Approval();
      approval.setApprovalType(ApprovalTypeStatus.PROCUREMENT_APPROVAL)
          .setPlanId(procurementPlan.getId())
          .setDescription("撤回上次审批操作.")
          .setResult("采购主管审批结果撤回");
      approvalRepository.save(approval);
    } else {
      throw new ResourceErrorException("当前项目状态有误，刷新后再试！");
    }
  }

  /**
   * 保存采购计划表.
   *
   * @param procurementPlan 物料需求计划基础信息
   * @param materials 物资列表
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void savePlanAndPlanMaterials(ProcurementPlan procurementPlan,
      List<PlanMaterial> materials) {
    //判断如果是更新则调用更新方法
    if (Objects.nonNull(procurementPlan.getId())) {
      updatePlanAndPlanMaterials(procurementPlan, materials);
      return;
    }
    procurementPlanRepository.save(procurementPlan);
    //判断是否保存成功并返回了id
    if (Objects.isNull(procurementPlan.getId())) {
      throw new ResourceErrorException("采购计划保存失败");
    }
    Long procurementPlanId = procurementPlan.getId();
    //循环保存
    materials.forEach(material -> {
      //判断信息是否填写完成
      if (Objects.isNull(material.getSupplyMode())) {
        throw new ResourceErrorException("信息未全部填写完成，请全部填写完成后再试！");
      }
      if (Objects.nonNull(material.getId())) {
        //判断是否已经被生成了采购计划
        Long id = planMaterialRepository.findById(material.getId())
            .orElseThrow(() -> new ResourceNotFoundException("需求物料未找到")).getProcurementPlanId();
        if (Objects.nonNull(id)) {
          throw new ResourceErrorException("当前信息已过期，请刷新后再试。");
        }
      }
      //判断供应方式为采购的才生成采购计划
      if ("采购".equals(material.getSupplyMode()) && Objects.nonNull(material.getPurchaseDate())) {
        material.setProcurementPlanId(procurementPlanId);
      }
      if ("库存供应".equals(material.getSupplyMode())) {
        material.setStatus(MaterialStatus.INVENTORY);
      }
      planMaterialRepository.save(material);
    });
  }

  /**
   * 更新采购计划表.
   *
   * @param procurementPlan 物料需求计划基础信息
   * @param materials 物资列表
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void updatePlanAndPlanMaterials(ProcurementPlan procurementPlan,
      List<PlanMaterial> materials) {
    procurementPlanRepository.save(procurementPlan);
    Long planId = procurementPlan.getId();
    //循环保存
    materials.forEach(material -> {
      if (Objects.isNull(material.getId())) {
        material.setProcurementPlanId(planId);
        planMaterialRepository.save(material);
      }
    });
  }

  /**
   * 获取紧急采购计划id.
   */
  @Override
  public Long getUrgentProcurementId() {
    ProcurementPlan procurementPlan = procurementPlanRepository.findByPlanType("紧急采购计划");
    // 紧急采购计划若存在则返回采购计划id
    if (Objects.nonNull(procurementPlan)) {
      return procurementPlan.getId();
    }
    // 若不存在则创建后再返回采购计划id
    procurementPlan = new ProcurementPlan();
    procurementPlan.setPlanType("紧急采购计划")
        .setApprovalStatus(ApprovalStatus.APPROVAL_OK)
        .setPlanStatus(PlanStatus.PROCUREMENT_OK)
        .setName("**紧急采购计划**");
    procurementPlanRepository.save(procurementPlan);
    return procurementPlan.getId();
  }
}
