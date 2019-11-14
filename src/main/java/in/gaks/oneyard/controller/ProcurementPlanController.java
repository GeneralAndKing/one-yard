package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.helper.VerifyParameter;
import in.gaks.oneyard.service.MaterialPlanSummaryService;
import in.gaks.oneyard.service.ProcurementPlanService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 采购计划接口.
 *
 * @author Japoul
 * @date 2019/11/13 下午23:31
 */
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(OneYard.PROCUREMENT_PLAN)
public class ProcurementPlanController extends BaseController<ProcurementPlan,
    ProcurementPlanService, Long> {

  private final @NonNull ProcurementPlanService procurementPlanService;


  /**
   * 根据id查询完整的采购计划表.
   *
   * @param id 采购计划表id
   * @return 查询的数据
   */
  @GetMapping("/getProcurementPlan")
  public HttpEntity<?> getMaterialPlan(@NotNull Long id) {
    Assert.notNull(id, "请求参数不合法");
    return ResponseEntity.ok(procurementPlanService.findByIdToMaterials(id));
  }

  /**
   * 审批采购计划.
   *
   * @param data 数据
   * @return 响应
   */
  @PostMapping("/approvalProcurementPlan")
  @VerifyParameter(required = {"procurementPlan#采购计划为必填项", "approval#审批表为必填项"})
  public HttpEntity<?> approvalMaterialPlan(@NotNull @RequestBody JSONObject data) {
    procurementPlanService.approvalProcurementPlan(
        data.getObject("procurementPlan", ProcurementPlan.class),
        data.getObject("approval", Approval.class));
    return ResponseEntity.ok().build();
  }
}