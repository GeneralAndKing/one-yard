package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.MATERIAL_PLAN_SUMMARY)
public interface MaterialPlanSummaryRepository extends BaseRepository<MaterialPlanSummary, Long> {

  /**
   * 根据创建者查询汇总表.
   *
   * @param createUser 创建者
   * @return 汇总表列表
   */
  @RestResource(path = "byCreateUser")
  List<MaterialPlanSummary> findAllByCreateUser(@Param("createUser") String createUser);

}