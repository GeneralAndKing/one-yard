package in.gaks.oneyard.controller;

import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import in.gaks.oneyard.service.MaterialPlanSummaryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(OneYard.MATERIAL_PLAN_SUMMARY)
public class MaterialPlanSummaryController extends BaseController<MaterialPlanSummary,
    MaterialPlanSummaryService, Long> {

  private final @NonNull MaterialPlanSummaryService materialPlanSummaryService;


  /**
   * 根据id查询完整的汇总表.
   *
   * @param id 汇总表id
   * @return 查询的数据
   */
  @GetMapping("/getMaterialPlanSummary")
  public HttpEntity<?> getMaterialPlan(@NotNull Long id) {
    Assert.notNull(id, "请求参数不合法");
    return ResponseEntity.ok(materialPlanSummaryService.findByIdToMaterialSummary(id));
  }

}
