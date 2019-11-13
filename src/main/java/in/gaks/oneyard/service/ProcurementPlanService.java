package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import in.gaks.oneyard.model.entity.ProcurementPlan;

/**
 * .
 *
 * @author Japoul
 * @date 2019/11/7 下午11:00
 */
public interface ProcurementPlanService extends BaseService<ProcurementPlan, Long> {

  /**
   * 根据id查询完整的采购计划表（包含列下所有需求物资）.
   *
   * @param id 汇总表id
   * @return 汇总表
   */
  ProcurementPlan findByIdToMaterials(Long id);

  /**
   * 采购主管/财务审批采购计划.
   *
   * @param procurementPlan 需求计划
   * @param approval 审批信息
   */
  void approvalProcurementPlan(ProcurementPlan procurementPlan, Approval approval);

}
