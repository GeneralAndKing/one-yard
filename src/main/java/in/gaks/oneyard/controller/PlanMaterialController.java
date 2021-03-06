package in.gaks.oneyard.controller;

import static in.gaks.oneyard.model.constant.OneYard.APPROVAL;
import static in.gaks.oneyard.model.constant.OneYard.PLAN_MATERIAL;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.helper.VerifyParameter;
import in.gaks.oneyard.service.PlanMaterialService;
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

import java.util.List;

/**
 * 需求物料接口.
 *
 * @author Japoul
 * @date 2019/11/16 下午22:31
 */
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(PLAN_MATERIAL)
public class PlanMaterialController extends BaseController<PlanMaterial,
        PlanMaterialService, Long> {

  private final @NonNull PlanMaterialService planMaterialService;

  /**
   * 退回需求.
   *
   * @return 执行结果
   */
  @PostMapping("/backPlanOrMaterial")
  @VerifyParameter(required = {"flag#退回类型为必填项", "planMaterial#计划为必填项", "approve#物料为必填项"})
  public HttpEntity<?> backPlanOrMaterial(@NotNull @RequestBody JSONObject data) {
    planMaterialService.backPlanOrMaterial(
            data.getObject("flag", Boolean.class),
            data.getObject(PLAN_MATERIAL, PlanMaterial.class),
            data.getObject(APPROVAL, Approval.class)
    );
    return ResponseEntity.ok().build();
  }

  /**
   * 合并数据.
   *
   * @return 执行结果
   */
  @PostMapping("/mergeMaterialPlan")
  @VerifyParameter(required = {"planMaterial#物料为必填项", "ids#合并的物料id为必填项"})
  public HttpEntity<?> mergeMaterialPlan(@NotNull @RequestBody JSONObject data) {
    PlanMaterial planMaterial = planMaterialService
            .mergeMaterialPlan(data.getObject(PLAN_MATERIAL, PlanMaterial.class),
                    data.getJSONArray("ids").toJavaList(Long.class));
    return ResponseEntity.ok(planMaterial);
  }

  /**
   * 拆分数据.
   *
   * @return 执行结果
   */
  @PostMapping("/splitMaterialPlan")
  @VerifyParameter(required = {"planMaterial#被拆分的物料为必填项", "newPlanMaterials#拆分成的物料列表为必填项"})
  public HttpEntity<?> splitMaterialPlan(@NotNull @RequestBody JSONObject data) {
    List<PlanMaterial> planMaterials = planMaterialService
            .splitMaterialPlan(data.getObject(PLAN_MATERIAL, PlanMaterial.class),
                    data.getJSONArray("newPlanMaterials").toJavaList(PlanMaterial.class));
    return ResponseEntity.ok(planMaterials);
  }
}
