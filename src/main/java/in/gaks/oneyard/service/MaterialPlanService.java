package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.PlanMaterial;
import java.util.List;

/**
 * .
 * @author Japoul
 * @date 2019/11/2 下午11:00
 */
public interface MaterialPlanService extends BaseService<MaterialDemandPlan, Long> {

  /**
   * 保存物料需求计划表.
   *
   * @param materialPlan 物料需求计划基础信息
   * @param materials 需求的物资列表
   */
  void savePlanAndPlanMaterials(MaterialDemandPlan materialPlan,
      List<PlanMaterial> materials);

  /**
   * 根据id查询完整的计划表.
   *
   * @param id 计划表id
   * @return 计划表
   */
  MaterialDemandPlan findByIdToMaterial(Long id);

  /**
   * 主管审批需求物料计划.
   *
   * @param materialDemandPlan 需求计划
   * @param approval 审批信息
   */
  void approvalMaterialPlan(MaterialDemandPlan materialDemandPlan, Approval approval);

  /**
   * 撤回审批.
   *
   * @param id 需求计划id
   */
  void withdrawApproval(Long id);
}
