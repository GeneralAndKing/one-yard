package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.OrderTerms;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.model.helper.VerifyParameter;
import in.gaks.oneyard.service.ProcurementOrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 采购订单接口.
 *
 * @author Japoul
 * @date 2019/11/13 下午23:31
 */
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(OneYard.PROCUREMENT_ORDER)
public class ProcurementOrderController extends BaseController<ProcurementOrder,
    ProcurementOrderService, Long> {

  private final @NonNull ProcurementOrderService procurementOrderService;

  /**
   * 审批采购订单.
   *
   * @param data 数据
   * @return 响应
   */
  @PostMapping("/approvalProcurementOrder")
  @VerifyParameter(required = {"procurementOrder#采购订单为必填项", "approval#审批表为必填项"})
  public HttpEntity<?> approvalMaterialOrder(@NotNull @RequestBody JSONObject data) {
    procurementOrderService.approvalProcurementOrder(
        data.getObject("procurementOrder", ProcurementOrder.class),
        data.getObject("approval", Approval.class));
    return ResponseEntity.ok().build();
  }

  /**
   * 撤回审批.
   *
   * @param data 数据
   * @return 响应
   */
  @PostMapping("/withdrawApproval")
  @VerifyParameter(required = {"procurementOrderId#采购订单id为必填项"})
  public HttpEntity<?> withdrawApproval(@NotNull @RequestBody JSONObject data) {
    procurementOrderService.withdrawApproval(
        data.getObject("procurementOrderId", Long.class)
    );
    return ResponseEntity.ok().build();
  }

  /**
   * 保存/修改采购订单表.
   *
   * @return 执行结果
   */
  @PostMapping("/procurementOrder")
  @VerifyParameter(required = {"procurementOrder#订单为必填项", "procurementMaterials#采购物料为必填项",
      "orderTerms#订单条款为必填项"})
  public HttpEntity<?> materialOrderCreate(@NotNull @RequestBody JSONObject data) {
    return ResponseEntity.ok(procurementOrderService.saveProcurementOrder(
        data.getObject("procurementOrder", ProcurementOrder.class),
        data.getJSONArray("procurementMaterials").toJavaList(ProcurementMaterial.class),
        data.getJSONArray("orderTerms").toJavaList(OrderTerms.class)
    ));
  }

  /**
   * 删除采购订单的明细信息（物料）.
   *
   * @param data 数据
   * @return 响应
   */
  @PostMapping("/deleteProcurementMaterial")
  @VerifyParameter(required = {"procurementMaterialId#采购订单明细信息物料id为必填项"})
  public HttpEntity<?> deleteProcurementMaterial(@NotNull @RequestBody JSONObject data) {
    procurementOrderService.deleteProcurementMaterial(
        data.getObject("procurementMaterialId", Long.class)
    );
    return ResponseEntity.ok().build();
  }
}
