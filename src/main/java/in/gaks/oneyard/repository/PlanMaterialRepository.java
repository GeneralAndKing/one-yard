package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.helper.OneYard;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.PLAN_MATERIAL)
public interface PlanMaterialRepository extends BaseRepository<PlanMaterial, Long> {

  /**
   * 根据计划id查询所需物资.
   */
  @RestResource(path = "byPlanId")
  List<PlanMaterial> findAllByPlanId(@Param("id") Long id);
}