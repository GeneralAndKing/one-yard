package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
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
  private final NotifyUtil notifyUtil;

  /**
   * 采购部门主管审批采购订单.
   *
   * @param procurementOrder 采购订单
   * @param approval 审批信息
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void approvalProcurementOrder(ProcurementOrder procurementOrder, Approval approval) {

  }

  /**
   * 撤回审批.
   *
   * @param procurementOrder 需求订单
   * @param role 角色类型
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void withdrawApproval(ProcurementOrder procurementOrder, String role) {

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
    if (Objects.isNull(procurementOrderId)) {
      throw new ResourceErrorException("采购订单保存失败");
    }
    // 给待采购物资赋值订单id 并让其绑定的物料变为已占用
    for (ProcurementMaterial material : materials) {
      material.setOrderId(procurementOrderId);
      if (Objects.nonNull(material.getPlanMaterialId())) {
        PlanMaterial planMaterial = planMaterialRepository.findById(material.getPlanMaterialId())
            .orElseThrow(() ->
                new ResourceNotFoundException("找不到绑定的需求物料"));
        if (planMaterial.getIsUseOrder()) {
          msg = "部分数据已经被其他采购订单占用，生成的订单数据可能不完整！";
          continue;
        }
        planMaterial.setIsUseOrder(true);
        planMaterialRepository.save(planMaterial);
      }
    }
    orderTerms.forEach(orderTerm -> orderTerm.setOrderId(procurementOrderId));
    orderTermsRepository.saveAll(orderTerms);
    return msg;
  }
}
