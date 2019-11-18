package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.constant.PlanStatus;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
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

  /**
   * 根据汇总表id查询计划表.
   *
   * @param summaryId 汇总表id
   * @return 计划列表
   */
  List<MaterialDemandPlan> findAllBySummaryId(@Param("summaryId") Long summaryId);

  /**
   * 根据创建者查询计划表.
   *
   * @param createUser 创建者
   * @return 计划列表
   */
  @RestResource(path = "byCreateUser")
  List<MaterialDemandPlan> findAllByCreateUser(@Param("createUser") String createUser);

  /**
   * 根据部门id列表查询需求计划表.
   *
   * @param departmentIds 部门id列表
   * @return .
   */
  @RestResource(path = "byDepartmentIds")
  @Query(value = "SELECT * FROM material_demand_plan WHERE department_id IN (:departmentIds) "
      + "and approval_status = (:approvalStatus) and plan_status = (:planStatus)",
      nativeQuery = true)
  List<MaterialDemandPlan> getMaterialDemandPlanByDepartmentId(
      @Param("departmentIds") List<Integer> departmentIds,
      @Param("approvalStatus") ApprovalStatus approvalStatus,
      @Param("planStatus") PlanStatus planStatus);
}