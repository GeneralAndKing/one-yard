package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.constant.PlanStatus;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
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
@RepositoryRestResource(path = OneYard.MATERIAL_DEMAND_PLAN)
public interface MaterialDemandPlanRepository extends BaseRepository<MaterialDemandPlan, Long> {

  /**
   * 根据两个状态查询计划表.
   *
   * @param planStatus 计划状态
   * @param approvalStatus 审批状态
   * @return 计划列表
   */
  @RestResource(path = "byStatusOfPlanAndApproval")
  List<MaterialDemandPlan> findAllByPlanStatusAndApprovalStatus(
      @Param("planStatus") PlanStatus planStatus,
      @Param("approvalStatus") ApprovalStatus approvalStatus);

}