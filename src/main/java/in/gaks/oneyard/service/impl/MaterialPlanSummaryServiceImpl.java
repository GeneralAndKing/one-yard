package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialPlanSummaryRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.service.MaterialPlanSummaryService;
import in.gaks.oneyard.service.PlanMaterialService;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 汇总表事务处理.
 *
 * @author Japoul
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class MaterialPlanSummaryServiceImpl extends BaseServiceImpl<MaterialPlanSummaryRepository,
    MaterialPlanSummary, Long>
    implements MaterialPlanSummaryService {

  private final @NonNull MaterialPlanSummaryRepository materialPlanSummaryRepository;
  private final @NonNull MaterialDemandPlanRepository materialPlanRepository;
  private final @NonNull PlanMaterialService planMaterialService;

  /**
   * 根据id获取完整的需求计划表.
   *
   * @param id 汇总表id
   * @return 完整的汇总表
   */
  @Override
  public MaterialPlanSummary findByIdToMaterialSummary(Long id) {
    MaterialPlanSummary summary = materialPlanSummaryRepository.findById(id)
        .orElseThrow(() -> new ResourceErrorException("需求汇总表查询失败"));
    List<MaterialDemandPlan> plans = materialPlanRepository.findAllBySummaryId(id);
    List<PlanMaterial> materials = new ArrayList<>();
    plans.forEach(plan -> {
      materials.addAll(planMaterialService.findAllByPlanId(plan.getId()));
    });
    summary.setPlanMaterials(materials);
    return summary;
  }

}
