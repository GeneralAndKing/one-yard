package in.gaks.oneyard.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.ChangeStatus;
import in.gaks.oneyard.model.constant.NotificationStatus;
import in.gaks.oneyard.model.constant.ProcurementApprovalStatus;
import in.gaks.oneyard.model.constant.ProcurementOrderPlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.ChangeHistory;
import in.gaks.oneyard.model.entity.Notification;
import in.gaks.oneyard.model.entity.OrderTerms;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.ChangeHistoryRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.OrderTermsRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementMaterialRepository;
import in.gaks.oneyard.repository.ProcurementOrderRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.ProcurementOrderService;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.HashSet;
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

  private static final String APPROVAL_OK = "审核通过";
  private static final String APPROVAL_PASS = "审核不通过";
  private static final String NOTIFICATION_NAME_APPROVAL_OK = "采购订单审批通过通知";
  private static final String NOTIFICATION_NAME_APPROVAL_PASS = "采购订单审批未通过通知";
  private static final String NOTIFICATION_NAME_CHANGED_OK = "变更采购订单审批通过通知";
  private static final String NOTIFICATION_NAME_CHANGED_PASS = "变更采购订单审批未通过通知";
  private static final String NOTIFICATION_NAME_CANCEL_OK = "取消采购订单审批通过通知";
  private static final String NOTIFICATION_NAME_CANCEL_PASS = "取消采购订单审批未通过通知";
  private static final String MSG = "您创建的采购订单 《%s》 %s";

  private final @NonNull ProcurementOrderRepository procurementOrderRepository;
  private final @NonNull ProcurementMaterialRepository procurementMaterialRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;
  private final @NonNull OrderTermsRepository orderTermsRepository;
  private final @NonNull ChangeHistoryRepository changeHistoryRepository;
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
    // 根据审批环节和审批意见发送不同的通知信息
    String res;
    res = approval.getResult();
    // 根据状态判断审批类型
    ProcurementOrderPlanStatus status = procurementOrder.getPlanStatus();
    JSONObject result = new JSONObject();
    // 如果是更改订单审批，审批通过时需要将变更信息
    if (ProcurementOrderPlanStatus.CHANGED.equals(status)) {
      result = approvalChangeProcurementOrder(procurementOrder, approval);
    } else if (ProcurementOrderPlanStatus.CANCEL.equals(status)) {
      result = approvalCancelProcurementOrder(procurementOrder, approval);
    } else {
      result.put("procurementOrder", procurementOrder);
      result.put("approval", approval);
    }
    procurementOrder = result.getObject("procurementOrder", ProcurementOrder.class);
    approval = result.getObject("approval", Approval.class);
    // 获取通知接收方id
    SysUser user = sysUserRepository.findFirstByUsername(procurementOrder.getCreateUser())
        .orElseThrow(() -> new ResourceNotFoundException("采购计划员查询失败"));
    // 发送通知并存入数据库
    Notification notification = constructNotification(status, res, procurementOrder.getName(),
        user.getId());
    // 检测用户是否在线发送通知
    notifyUtil.sendMessage(user.getId().toString(), notification);
    notificationRepository.save(notification);
    // 更新订单审核状态
    procurementOrderRepository.save(procurementOrder);
    // 保存审批信息，回溯流程
    approvalRepository.save(approval);
  }

  /**
   * 变更订单审批.
   *
   * @param procurementOrder 采购订单
   * @param approval 审批信息
   */
  private JSONObject approvalChangeProcurementOrder(ProcurementOrder procurementOrder,
      Approval approval) {
    List<ChangeHistory> changeHistories = changeHistoryRepository
        .findAllByOrderIdAndStatus(procurementOrder.getId(), ChangeStatus.APPROVAL_ING);
    if (APPROVAL_OK.equals(approval.getResult())) {
      // 通过则修改变更历史状态
      approval.setResult("变更请求审批通过");
      changeHistories.forEach(changeHistory -> changeHistory.setStatus(ChangeStatus.APPROVAL_OK));
    } else if (APPROVAL_PASS.equals(approval.getResult())) {
      // 不通过则恢复数据并修改变更历史状态
      approval.setResult("变更请求审批未通过");
      changeHistories.forEach(c -> {
        c.setStatus(ChangeStatus.APPROVAL_PASS);
        // 复原数据
        ProcurementMaterial p = procurementMaterialRepository
            .findById(c.getProcurementMaterialId())
            .orElseThrow(() -> new ResourceNotFoundException("变更历史关联的待采购物料查询失败！"));
        p.setChargeNumber(c.getOldChargeNumber()).setChargeUnit(c.getOldChargeUnit())
            .setProcurementNumber(c.getOldNumber()).setUnitPrice(c.getNewPrice());
        procurementMaterialRepository.save(p);
      });
    }
    // 保存变更历史
    changeHistoryRepository.saveAll(changeHistories);
    // 设置订单状态
    procurementOrder.setApprovalStatus(ProcurementApprovalStatus.APPROVAL_OK)
        .setPlanStatus(ProcurementOrderPlanStatus.EFFECTIVE);
    // 压入 JSONObject 并返回
    JSONObject result = new JSONObject();
    result.put("procurementOrder", procurementOrder);
    result.put("approval", approval);
    return result;
  }

  /**
   * 取消订单审批.
   *
   * @param procurementOrder 采购订单
   * @param approval 审批信息
   */
  private JSONObject approvalCancelProcurementOrder(ProcurementOrder procurementOrder,
      Approval approval) {
    if (APPROVAL_OK.equals(approval.getResult())) {
      approval.setResult("取消订单请求审批通过");
      // 设置订单为已作废
      procurementOrder.setPlanStatus(ProcurementOrderPlanStatus.CANCEL);
      // 恢复订单下关联需求物资数据，解除占用
      // 1.获取与需求物资关联的待采购物资列表
      List<ProcurementMaterial> procurementMaterials = procurementMaterialRepository
          .findAllByOrderIdAndPlanMaterialIdIsNotNull(procurementOrder.getId());
      // 2.循环解除占用
      procurementMaterials.forEach(p -> {
        PlanMaterial planMaterial = planMaterialRepository
            .findById(p.getPlanMaterialId())
            .orElseThrow(() -> new ResourceNotFoundException("找不到关联的需求物料信息"));
        // 接触关联需求物料的占用
        planMaterial.setIsUseOrder(false);
        planMaterialRepository.save(planMaterial);
        p.setPlanMaterialId(null);
      });
      // 解除待采购和需求物资的关联
      procurementMaterialRepository.saveAll(procurementMaterials);
    } else if (APPROVAL_PASS.equals(approval.getResult())) {
      approval.setResult("取消订单请求审批未通过");
      // 恢复订单状态至取消前（审批通过/已生效）
      procurementOrder.setPlanStatus(ProcurementOrderPlanStatus.EFFECTIVE);
    }
    // 都是审批通过
    procurementOrder.setApprovalStatus(ProcurementApprovalStatus.APPROVAL_OK);
    // 压入 JSONObject 并返回
    JSONObject result = new JSONObject();
    result.put("procurementOrder", procurementOrder);
    result.put("approval", approval);
    return result;
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
    if (!ProcurementApprovalStatus.APPROVAL_ING.equals(p.getApprovalStatus())
        || !ProcurementOrderPlanStatus.APPROVAL.equals(p.getPlanStatus())) {
      throw new ResourceErrorException("该采购订单状态发生改变，不可撤回，请刷新后再试！");
    }
    p.setApprovalStatus(ProcurementApprovalStatus.NO_SUBMIT)
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
    // 逻辑删除失效数据
    checkProcurementOrder(procurementOrderId, materials, orderTerms);
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
          // 移除被占用的物料数据
          materials.remove(material);
          msg = "部分数据已经被其他采购订单占用，生成的订单数据可能不完整！";
          continue;
        }
        planMaterial.setIsUseOrder(true);
        planMaterialRepository.save(planMaterial);
      }
    }
    // 新增物料直接保存进待采购物料中，通过检测的也保存
    procurementMaterialRepository.saveAll(materials);
    // 保存订单条款
    orderTerms.forEach(orderTerm -> orderTerm.setOrderId(procurementOrderId));
    orderTermsRepository.saveAll(orderTerms);
    return msg;
  }

  /**
   * 检查无效的物料和条款数据从数据库中逻辑删除.
   *
   * @param procurementOrderId 订单id
   * @param materials 采购订单物料
   * @param orderTerms 订单条款
   */
  private void checkProcurementOrder(Long procurementOrderId, List<ProcurementMaterial> materials,
      List<OrderTerms> orderTerms) {
    // 判断订单状态是否正确
    ProcurementOrder procurementOrder = procurementOrderRepository
        .findById(procurementOrderId)
        .orElseThrow(() -> new ResourceNotFoundException("查询采购订单失败"));
    if (!ProcurementApprovalStatus.NO_SUBMIT.equals(procurementOrder.getApprovalStatus())
        || !ProcurementOrderPlanStatus.NO_SUBMIT.equals(procurementOrder.getPlanStatus())) {
      throw new ResourceErrorException("当前采购订单状态不对");
    }
    // 获取数据库待采购物料数据进行对比
    List<ProcurementMaterial> procurementMaterialDB = procurementMaterialRepository
        .findAllByOrderId(procurementOrderId);
    // set1 存放为数据库数据，
    HashSet<Long> set1 = new HashSet<>();
    HashSet<Long> set2 = new HashSet<>();
    // 遍历物料 ID 存入 Set
    procurementMaterialDB.forEach(material -> set1.add(material.getId()));
    materials.forEach(material -> set2.add(material.getId()));
    // 做差集
    SetView differenceMaterials = Sets.difference(set1, set2);
    // 执行删除
    differenceMaterials.forEach(id -> deleteProcurementMaterial((Long) id));
    // 清除 Set
    set1.clear();
    set2.clear();
    // 获取数据库订单条款数据进行对比
    List<OrderTerms> orderTermsDB = orderTermsRepository.findAllByOrderId(procurementOrderId);
    // 遍历条款 ID 存入 Set
    orderTermsDB.forEach(orderTerm -> set1.add(orderTerm.getId()));
    orderTerms.forEach(orderTerm -> set2.add(orderTerm.getId()));
    // 做差集
    SetView differenceOrderTerms = Sets.difference(set1, set2);
    // 执行删除
    differenceOrderTerms.forEach(id -> deleteOrderTerms(((Long) id)));
  }

  /**
   * 删除采购订单的明细信息（物料）.
   *
   * @param procurementMaterialId 待采购物料id
   */
  @Transactional(rollbackOn = Exception.class)
  void deleteProcurementMaterial(Long procurementMaterialId) {
    ProcurementMaterial procurementMaterial = procurementMaterialRepository
        .findById(procurementMaterialId)
        .orElseThrow(() -> new ResourceNotFoundException("找不到该待采购物料"));
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

  /**
   * 删除采购订单的订单条款.
   *
   * @param orderTermsId 订单条款id
   */
  @Transactional(rollbackOn = Exception.class)
  void deleteOrderTerms(Long orderTermsId) {
    OrderTerms orderTerms = orderTermsRepository
        .findById(orderTermsId)
        .orElseThrow(() -> new ResourceNotFoundException("找不到该订单条款"));
    orderTerms.setOrderId(null).setIsEnable(false);
    orderTermsRepository.save(orderTerms);
  }

  /**
   * 构造通知.
   *
   * @param status 审批状态/类型
   * @param res 审批意见
   * @param orderName 订单名称
   * @param recId 接受者id
   * @return 通知对象
   */
  private Notification constructNotification(ProcurementOrderPlanStatus status,
      String res, String orderName, Long recId) {
    Notification notification = new Notification();
    String name = "";
    String msg = "";
    if (ProcurementOrderPlanStatus.CHANGED.equals(status)) {
      if (APPROVAL_OK.equals(res)) {
        name = NOTIFICATION_NAME_CHANGED_OK;
        msg = "提交的更改订单的请求审批通过了！订单已生效！";
      } else if (APPROVAL_PASS.equals(res)) {
        name = NOTIFICATION_NAME_CHANGED_PASS;
        msg = "提交的更改订单的请求因为某些原因审批未通过！（具体请查看订单详情页->审批流程）";
      }
    } else if (ProcurementOrderPlanStatus.CANCEL.equals(status)) {
      if (APPROVAL_OK.equals(res)) {
        name = NOTIFICATION_NAME_CANCEL_OK;
        msg = "提交的取消订单的请求审批通过了！订单已失效！";
      } else if (APPROVAL_PASS.equals(res)) {
        name = NOTIFICATION_NAME_CANCEL_PASS;
        msg = "提交的取消订单的请求因为某些原因审批未通过！（具体请查看订单详情页->审批流程）";
      }
    } else if (ProcurementOrderPlanStatus.APPROVAL.equals(status)) {
      if (APPROVAL_OK.equals(res)) {
        name = NOTIFICATION_NAME_APPROVAL_OK;
        msg = "提交的审批通过了！";
      } else if (APPROVAL_PASS.equals(res)) {
        name = NOTIFICATION_NAME_APPROVAL_PASS;
        msg = "提交的审批因为某些原因未通过！（具体请查看订单详情页->审批流程）";
      }
    } else {
      throw new ResourceErrorException("订单审批状态错误！");
    }
    notification.setStatus(NotificationStatus.UNREAD).setReceiverId(recId)
        .setMessage(String.format(MSG, orderName, msg)).setName(name);
    return notification;
  }

}
