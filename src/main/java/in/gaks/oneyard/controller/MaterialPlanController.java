package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.service.MaterialPlanService;
import java.util.List;
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
 * 物料需求计划接口.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @author Japoul
 * @date 2019/11/2 上午9:31
 */
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(OneYard.MATERIAL_DEMAND_PLAN)
public class MaterialPlanController extends BaseController<MaterialDemandPlan,
    MaterialPlanService, Long> {

  private final @NonNull MaterialPlanService materialPlanService;

  /**
   * 保存/修改物料计划表.
   *
   * @return 执行结果
   */
  @PostMapping("/materialPlan")
  public HttpEntity<?> materialPlanCreate(@NotNull @RequestBody JSONObject data) {
    MaterialDemandPlan materialPlan = data.getObject("materialPlan", MaterialDemandPlan.class);
    List<PlanMaterial> materials = data.getJSONArray("desserts").toJavaList(PlanMaterial.class);
    Assert.notNull(materialPlan, "请求参数不合法");
    Assert.notNull(materials, "请求参数不合法");
    materialPlanService.savePlanAndPlanMaterials(materialPlan, materials);
    return ResponseEntity.ok().build();
  }

  /**
   * 根据id查询完整的计划表.
   *
   * @param id 计划表id
   * @return 查询的数据
   */
  @GetMapping("/materialPlan")
  public HttpEntity<?> getMaterialPlan(@NotNull Long id) {
    Assert.notNull(id, "请求参数不合法");
    return ResponseEntity.ok(materialPlanService.findByIdToMaterial(id));
  }

  /**
   * 主管审批需求物料计划.
   *
   * @param data 数据
   * @return .
   */
  @PostMapping("/approvalMaterialPlan")
  public HttpEntity<?> approvalMaterialPlan(@NotNull @RequestBody JSONObject data) {
    MaterialDemandPlan materialPlan = data.getObject("materialPlan", MaterialDemandPlan.class);
    Approval approval = data.getObject("approval", Approval.class);
    Assert.notNull(materialPlan, "请求参数不合法");
    Assert.notNull(approval, "请求参数不合法");
    materialPlanService.approvalMaterialPlan(materialPlan, approval);
    return ResponseEntity.ok().build();
  }
}
