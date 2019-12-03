package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.OrderTerms;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月30日 下午8:05:18
 */
@RepositoryRestResource(path = OneYard.ORDER_TERMS)
public interface OrderTermsRepository extends BaseRepository<OrderTerms, Long> {

  /**
   * 根据订单id查询订单条款.
   *
   * @param orderId 订单id
   * @return .
   */
  @RestResource(path = "byOrderId")
  List<OrderTerms> findAllByOrderId(@Param("orderId") Long orderId);

}