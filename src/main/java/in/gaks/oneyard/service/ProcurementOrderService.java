package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.OrderTerms;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import java.util.List;

/**
 * 采购订单service.
 *
 * @author Japoul
 * @date 2019/12/1 下午16:00
 */
public interface ProcurementOrderService extends BaseService<ProcurementOrder, Long> {

  /**
   * 采购主管/财务审批采购订单.
   *
   * @param procurementOrder 采购订单
   * @param approval         审批信息
   */
  void approvalProcurementOrder(ProcurementOrder procurementOrder, Approval approval);

  /**
   * 撤回审批.
   *
   * @param procurementOrderId 采购订单
   */
  void withdrawApproval(Long procurementOrderId);

  /**
   * 保存采购订单表.
   *
   * @param procurementOrder 采购订单
   * @param materials        采购物料
   * @param orderTerms       订单条款
   * @return .
   */
  String saveProcurementOrder(ProcurementOrder procurementOrder,
      List<ProcurementMaterial> materials, List<OrderTerms> orderTerms);

  /**
   * 变更采购订单.
   *
   * @param id        采购订单 id
   * @param materials 明细信息
   */
  void changeProcurementOrder(Long id, List<ProcurementMaterial> materials);

  /**
   * 删除采购订单.
   *
   * @param id 采购订单id
   */
  void deleteProcurementOrder(Long id);
}
