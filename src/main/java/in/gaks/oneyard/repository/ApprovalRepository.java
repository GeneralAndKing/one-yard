package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.ApprovalTypeStatus;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.Approval;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.AUTH)
public interface ApprovalRepository extends BaseRepository<Approval, Long> {

  /**
   * 根据计划id和审批类型查询审批流程.
   *
   * @param planId 计划id
   * @param approvalType 审批类型
   * @return .
   */
  @RestResource(path = "byPlanIdAndApprovalType")
  List<Approval> findByPlanIdAndApprovalType(@Param("planId") Long planId,
      @Param("approvalType") ApprovalTypeStatus approvalType);
}