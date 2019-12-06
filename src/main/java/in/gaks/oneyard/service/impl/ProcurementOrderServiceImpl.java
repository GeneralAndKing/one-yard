package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.ProcurementOrderPlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.OrderTerms;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.OrderTermsRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementMaterialRepository;
import in.gaks.oneyard.repository.ProcurementOrderRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.ProcurementOrderService;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 采购订单表事务处理.
 *
 * @author Japoul
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class ProcurementOrderServiceImpl extends BaseServiceImpl<ProcurementOrderRepository,
    ProcurementOrder, Long>
    implements ProcurementOrderService {

  private final @NonNull ProcurementOrderRepository procurementOrderRepository;
  private final @NonNull ProcurementMaterialRepository procurementMaterialRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;
  private final @NonNull OrderTermsRepository orderTermsRepository;
  private final @NonNull ApprovalRepository approvalRepository;
  private final @NonNull SysUserRepository sysUserRepository;
  private final @NonNull NotificationRepository notificationRepository;
  private final @NonNull NotifyUtil notifyUtil;

  /**
   * 采购部门主管审批采购订单.
   *
   * @param procurementOrder 采购订单
   * @param approval 审批信息
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void approvalProcurementOrder(ProcurementOrder procurementOrder, Approval approval) {
    ProcurementOrderPlanStatus status = procurementOrder.getPlanStatus();
    if (ProcurementOrderPlanStatus.CHANGED.equals(status)) {

    } else if (ProcurementOrderPlanStatus.CANCEL.equals(status)) {

    }
  }

  /**
   * 撤回审批请求.
   *
   * @param procurementOrderId 采购订单id
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void withdrawApproval(Long procurementOrderId) {
    ProcurementOrder p = procurementOrderRepository.findById(procurementOrderId)
        .orElseThrow(() -> new ResourceNotFoundException("找不到对应的采购订单"));
    if (!ApprovalStatus.APPROVAL_ING.equals(p.getApprovalStatus())
        || !ProcurementOrderPlanStatus.APPROVAL.equals(p.getPlanStatus())) {
      throw new ResourceErrorException("该采购订单状态发生改变，不可撤回，请刷新后再试！");
    }
    p.setApprovalStatus(ApprovalStatus.NO_SUBMIT)
        .setPlanStatus(ProcurementOrderPlanStatus.NO_SUBMIT);
    procurementOrderRepository.save(p);
  }

  /**
   * 保存采购订单表.
   *
   * @param procurementOrder 采购订单
   * @param materials 采购物料
   * @param orderTerms 订单条款
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public String saveProcurementOrder(ProcurementOrder procurementOrder,
      List<ProcurementMaterial> materials, List<OrderTerms> orderTerms) {
    // 创建返回信息
    String msg = "保存采购订单成功！";
    // 保存采购订单
    procurementOrderRepository.save(procurementOrder);
    // 获取订单id并检测
    Long procurementOrderId = procurementOrder.getId();
    // 给待采购物资赋值订单id 并让其绑定的物料变为已占用
    for (ProcurementMaterial material : materials) {
      material.setOrderId(procurementOrderId);
      // 若关联id不为空则检测是否被占用
      if (Objects.nonNull(material.getPlanMaterialId())) {
        PlanMaterial planMaterial = planMaterialRepository.findById(material.getPlanMaterialId())
            .orElseThrow(() ->
                new ResourceNotFoundException("找不到绑定的需求物料"));
        // 判断如果该物料已经被占用了则不保存该物料
        if (planMaterial.getIsUseOrder() && !material.getOrderId()
            .equals(procurementOrder.getId())) {
          msg = "部分数据已经被其他采购订单占用，生成的订单数据可能不完整！";
          continue;
        }
        planMaterial.setIsUseOrder(true);
        planMaterialRepository.save(planMaterial);
      }
      // 新增物料直接保存进待采购物料中，通过检测的也保存
      procurementMaterialRepository.save(material);
    }
    orderTerms.forEach(orderTerm -> orderTerm.setOrderId(procurementOrderId));
    orderTermsRepository.saveAll(orderTerms);
    return msg;
  }


  @Override
  @Transactional(rollbackOn = Exception.class)
  public String updateProcurementOrder(ProcurementOrder procurementOrder, List<ProcurementMaterial> materials, List<OrderTerms> orderTerms) {
    // 创建返回信息
    String msg = "保存采购订单成功！";
    Long procurementOrderId = procurementOrder.getId();
    List<ProcurementMaterial> procurementMaterials = procurementMaterialRepository.findAllByOrderId(procurementOrderId);
    //算出procurementMaterials中不包含materials的元素 删除 包含的更新
//    procurementMaterials.stream().filter(procurementMaterial -> {
//      materials.stream()
//    })
    //订单条款一样
    return null;
  }

  /**
   * 删除采购订单的明细信息（物料）.
   *
   * @param procurementMaterialId 待采购物料id
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void deleteProcurementMaterial(Long procurementMaterialId) {
    ProcurementMaterial procurementMaterial = procurementMaterialRepository
        .findById(procurementMaterialId)
        .orElseThrow(() -> new ResourceNotFoundException("找不到该待采购物料"));
    // 判断删除时状态是否正确
    ProcurementOrder procurementOrder = procurementOrderRepository
        .findById(procurementMaterial.getOrderId())
        .orElseThrow(() -> new ResourceNotFoundException("查询采购订单失败"));
    if (ApprovalStatus.APPROVAL_ING.equals(procurementOrder.getApprovalStatus())
        || ApprovalStatus.APPROVAL_OK.equals(procurementOrder.getApprovalStatus())
        || ProcurementOrderPlanStatus.EFFECTIVE.equals(procurementOrder.getPlanStatus())) {
      throw new ResourceErrorException("当前订单状态不对");
    }
    // 若 planMaterialId 不为空说明是选单的数据，需接触关联
    if (Objects.nonNull(procurementMaterial.getPlanMaterialId())) {
      PlanMaterial planMaterial = planMaterialRepository
          .findById(procurementMaterial.getPlanMaterialId())
          .orElseThrow(() -> new ResourceNotFoundException("找不到关联的需求物料信息"));
      // 接触关联需求物料的占用
      planMaterial.setIsUseOrder(false);
      planMaterialRepository.save(planMaterial);
    }
    procurementMaterial.setOrderId(null).setIsEnable(false);
    procurementMaterialRepository.save(procurementMaterial);
  }
}
