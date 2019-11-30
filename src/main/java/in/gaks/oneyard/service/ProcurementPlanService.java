package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.entity.dto.ProcurementPlanDto;
import java.util.List;

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
  List<ProcurementPlanDto> findByIdToMaterials(Long id);

  /**
   * 采购主管/财务审批采购计划.
   *
   * @param procurementPlan 需求计划
   * @param approval 审批信息
   */
  void approvalProcurementPlan(ProcurementPlan procurementPlan, Approval approval);

  /**
   * 撤回审批.
   *
   * @param procurementPlan 需求计划
   * @param role 角色类型
   */
  void withdrawApproval(ProcurementPlan procurementPlan, String role);

  /**
   * 保存采购计划表.
   *
   * @param procurementPlan 物料需求计划基础信息
   * @param materials 物资列表
   */
  void savePlanAndPlanMaterials(ProcurementPlan procurementPlan,
      List<PlanMaterial> materials);

  /**
   * 更新采购计划表.
   *
   * @param procurementPlan 物料需求计划基础信息
   * @param materials 物资列表
   */
  void updatePlanAndPlanMaterials(ProcurementPlan procurementPlan,
      List<PlanMaterial> materials);

  /**
   * 获取紧急采购计划id.
   *
   * @return .
   */
  Long getUrgentProcurementId();
}
