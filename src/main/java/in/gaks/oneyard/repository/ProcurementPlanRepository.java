package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.constant.PlanStatus;
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
  @Query(value = "select p.id, p.name from procurement_plan p "
      + "where p.plan_status = (:planStatus) and p.approval_status = (:approvalStatus) "
      + "and p.plan_type != '紧急采购计划' and p.is_enable = 1",
      nativeQuery = true)
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
  @Query(value = "select p.* from procurement_plan p "
      + "where p.create_user = (:createUser) and p.plan_type != '紧急采购计划' and p.is_enable = 1",
      nativeQuery = true)
  List<ProcurementPlan> searchAllByCreateUser(@Param("createUser") String createUser);

  /**
   * 通过计划状态获取数量.
   *
   * @param planStatus 计划状态
   * @return 结果
   */
  @RestResource(path = "count")
  Long countByPlanStatus(@Param("planStatus") PlanStatus planStatus);

  /**
   * 根据采购计划类型查询采购计划.
   *
   * @param planType 采购类型
   * @return .
   */
  @RestResource(path = "byPlanType")
  List<ProcurementPlan> findAllByPlanType(@Param("planType") String planType);
}