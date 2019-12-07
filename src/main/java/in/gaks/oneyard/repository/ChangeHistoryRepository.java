package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.ChangeHistory;
import in.gaks.oneyard.model.constant.OneYard;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年12月3日 下午5:06:14
 */
@RepositoryRestResource(path = OneYard.CHANGE_HISTORY)
public interface ChangeHistoryRepository extends BaseRepository<ChangeHistory, Long> {

  /**
   * 根据订单 id 查询.
   *
   * @param orderId 订单 id
   * @return 结果
   */
  @RestResource(path = "byOrderId")
  List<ChangeHistory> findAllByOrderId(@Param("orderId") Long orderId);

}