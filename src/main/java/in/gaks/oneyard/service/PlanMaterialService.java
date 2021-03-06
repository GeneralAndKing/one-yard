package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.PlanMaterial;
import java.util.List;

/**
 * Repository.
 *
 * @author Japoul
 * @date 2019年11月11日 下午10:17:20
 */
public interface PlanMaterialService extends BaseService<PlanMaterial, Long> {

  /**
   * 根据计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @param type 调用该方法的类型
   * @return 完整的需求物资数据
   */
  List<PlanMaterial> findAllByPlanId(Long id, boolean type);

  /**
   * 根据采购计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   * @since 1.0.0
   * @deprecated 因某些问题而被废弃
   */
  @Deprecated(since = "1.0.0")
  List<PlanMaterial> findAllByProcurementPlanId(Long id);

  /**
   * 根据需求计划id查询所需部门.
   *
   * @param planId 需求计划id
   */
  String getDepartmentNameByPlanId(Long planId);

  /**
   * 退回需求.
   *
   * @param flag true 退回一条物资 false 退回一个计划
   * @param planMaterial 需求物资
   * @param approve 审批意见
   */
  void backPlanOrMaterial(boolean flag, PlanMaterial planMaterial, Approval approve);


  /**
   * 物料合并.
   * @param planMaterial 合并后的物料
   * @param ids 待合并的ids
   * @return 合并后的物料
   */
  PlanMaterial mergeMaterialPlan(PlanMaterial planMaterial,List<Long> ids);

  /**
   * 拆分物料.
   *
   * @param planMaterial 被拆分的物料
   * @param newPlanMaterials 拆分成的物料列表
   * @return 拆分后的物料List
   */
  List<PlanMaterial> splitMaterialPlan(PlanMaterial planMaterial, List<PlanMaterial> newPlanMaterials);
}
