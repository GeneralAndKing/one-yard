package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.PLAN_MATERIAL)
public interface PlanMaterialRepository extends BaseRepository<PlanMaterial, Long> {

  /**
   * 根据需求计划id查询所需物资.
   *
   * @param id 需求计划id
   * @return 物资列表
   */
  @RestResource(path = "byPlanId")
  List<PlanMaterial> findAllByPlanId(@Param("id") Long id);

  /**
   * 根据需求计划id和物料状态查询所需物资.
   *
   * @param id 需求计划id
   * @param status 状态
   * @return 物资列表
   */
  List<PlanMaterial> findAllBySummaryIdAndStatusAndProcurementPlanIdIsNull(@Param("id") Long id,
      @Param("status") MaterialStatus status);

  /**
   * 根据采购计划id查询所需物资.
   *
   * @param id 采购计划id
   * @param materialStatus 物料状态
   * @return 物资列表
   */
  List<PlanMaterial> findAllByProcurementPlanIdAndStatus(@Param("id") Long id,
      @Param("materialStatus") MaterialStatus materialStatus);

  /**
   * 根据采购计划id和物料id以及供应方式查询在途数量.
   *
   * @param ids 采购计划id
   * @param id 指定的物料id
   * @param supplyMode 指定的物料id
   * @return 指定物料的在途数量
   */
  @Query(value = "select pm.supply_number from plan_material pm "
      + "where pm.procurement_plan_id in (:ids) and pm.material_id = (:id) "
      + "and pm.is_enable = 1 and pm.supply_mode = (:supplyMode) "
      + "and pm.status = 0",
      nativeQuery = true)
  List<Long> searchByProcurementPlanIdsAndSupplyMode(@Param("ids") List<Long> ids,
      @Param("id") Long id, @Param("supplyMode") String supplyMode);

  /**
   * 根据汇总id查询所有物资.
   *
   * @param id 汇总id
   */
  List<PlanMaterial> findAllBySummaryId(@Param("id") Long id);

  /**
   * 根据供应方式和物资状态查询.
   *
   * @param mode 供应方式
   * @param status 物资状态
   * @return .
   */
  List<PlanMaterial> findAllBySupplyModeAndStatus(String mode, MaterialStatus status);

  /**
   * 根据采购计划id获取采购订单选单所需的物料.
   *
   * @param procurementPlanId 采购计划id
   * @return .
   */
  @RestResource(path = "byProcurementPlanId")
  @Query(value = "select pm.* from plan_material pm "
      + "where pm.procurement_plan_id = (:procurementPlanId) and pm.is_use_order = 0 "
      + "and pm.supply_mode = `采购` and pm.is_enable = 1 and pm.status = 0 ", nativeQuery = true)
  List<PlanMaterial> getAllByProcurementPlanId(@Param("procurementPlanId") Long procurementPlanId);
}