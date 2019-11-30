package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
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
@RepositoryRestResource(path = OneYard.PROCUREMENT_MATERIAL)
public interface ProcurementMaterialRepository extends BaseRepository<ProcurementMaterial, Long> {

  /**
   * 根据订单id查询待采购物料.
   *
   * @param orderId 订单id
   * @return .
   */
  List<ProcurementMaterial> findAllByOrderId(@Param("orderId") Long orderId);
}