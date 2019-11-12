package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialPlanSummaryRepository;
import in.gaks.oneyard.service.MaterialPlanSummaryService;
import in.gaks.oneyard.service.PlanMaterialService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
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
        .orElseThrow(() -> new ResourceNotFoundException("需求汇总表查询失败"));
    List<MaterialDemandPlan> plans = materialPlanRepository.findAllBySummaryId(id);
    List<PlanMaterial> materials = new ArrayList<>();
    plans.forEach(plan -> {
      materials.addAll(planMaterialService.findAllByPlanId(plan.getId()));
    });
    summary.setPlanMaterials(materials);
    return summary;
  }

  /**
   * 自动汇总，若存在则返回id，不存在则创建后再返回id.
   *
   * @param materialDemandPlan .
   * @return 汇总表id.
   */
  @Override
  public Long summaryMaterialPlan(MaterialDemandPlan materialDemandPlan) {
    String type = materialDemandPlan.getPlanType();
    String year = materialDemandPlan.getMonth().substring(0, 4);
    String summaryName = null;
    switch (type) {
      case "年度计划":
        summaryName = year.concat("年份年度计划汇总");
        break;
      case "月度计划":
        String month = materialDemandPlan.getMonth().substring(4, 6);
        summaryName = year.concat("年").concat(month).concat("月份月度计划汇总");
        break;
      default:
    }
    MaterialPlanSummary summary = materialPlanSummaryRepository.findByName(summaryName);
    if (Objects.nonNull(summary)) {
      return summary.getId();
    }
    summary.setName(summaryName);
    materialPlanSummaryRepository.save(summary);
    return summary.getId();
  }
}
