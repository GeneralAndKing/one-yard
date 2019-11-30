package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.model.constant.OneYard;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月30日 下午8:05:18
 */
@RepositoryRestResource(path = OneYard.PROCUREMENT_ORDER)
public interface ProcurementOrderRepository extends BaseRepository<ProcurementOrder, Long> {

  /**
   * 根据订单状态查询采购订单.
   *
   * @param planStatus 订单状态
   * @param approvalStatus 审批状态
   * @return 订单列表
   */
  List<ProcurementOrder> findAllByPlanStatusAndApprovalStatus(
      @Param("planStatus") String planStatus,
      @Param("approvalStatus") String approvalStatus);

}