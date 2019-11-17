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
  List<PlanMaterial> findAllByPlanIdAndStatusAndProcurementPlanIdIsNull(@Param("id") Long id,
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
}