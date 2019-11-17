package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.constant.PlanStatus;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.entity.SysRole;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月13日 下午10:34:45
 */
@RepositoryRestResource(path = OneYard.PROCUREMENT_PLAN)
public interface ProcurementPlanRepository extends BaseRepository<ProcurementPlan, Long> {

  /**
   * 根据状态查询采购计划.
   *
   * @param planStatus 计划id
   * @param approvalStatus 审批id
   * @return 。
   */
  @RestResource(path = "byStatus")
  List<ProcurementPlan> findAllByPlanStatusAndApprovalStatus(
      @Param("planStatus") PlanStatus planStatus,
      @Param("approvalStatus") ApprovalStatus approvalStatus);

  /**
   * 根据创建者查询采购计划.
   *
   * @param createUser 创建者的username
   * @return .
   */
  @RestResource(path = "byCreateUser")
  List<ProcurementPlan> findAllByCreateUser(@Param("createUser") String createUser);

}