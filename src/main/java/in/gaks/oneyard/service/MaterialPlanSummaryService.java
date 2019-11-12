package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;

/**
 * .
 *
 * @author Japoul
 * @date 2019/11/7 下午11:00
 */
public interface MaterialPlanSummaryService extends BaseService<MaterialPlanSummary, Long> {

  /**
   * 根据id查询完整的汇总表.
   *
   * @param id 汇总表id
   * @return 汇总表
   */
  MaterialPlanSummary findByIdToMaterialSummary(Long id);

  /**
   * 自动汇总，若存在则返回id，不存在则创建后再返回id.
   *
   * @param materialDemandPlan .
   * @return 汇总表id.
   */
  Long summaryMaterialPlan(MaterialDemandPlan materialDemandPlan);
}
