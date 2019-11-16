package in.gaks.oneyard.repository.dto;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.dto.ProcurementPlanDto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/16 下午3:15
 */
@RepositoryRestResource(path = "procurementPlanInfo")
public interface ProcurementPlanDtoRepository extends BaseRepository<ProcurementPlanDto, Long> {

  /**
   * 查询完整信息.
   *
   * @param id 计划id
   * @return 结果
   */
  @RestResource(path = "infoById")
  @Query(value = "select * from pm_info where procurement_plan_id = :id", nativeQuery = true)
  List<ProcurementPlanDto> searchInfoById(@Param("id") Long id);

}
