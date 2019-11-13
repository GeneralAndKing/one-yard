package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.PlanMaterial;
import java.util.List;
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
   * 根据采购计划id查询所需物资.
   *
   * @param id 采购计划id
   * @param materialStatus 物料状态
   * @return 物资列表
   */
  List<PlanMaterial> findAllByProcurementPlanIdAndStatus(@Param("id") Long id,
      @Param("materialStatus") MaterialStatus materialStatus);

}