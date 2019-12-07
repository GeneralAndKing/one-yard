package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.constant.OneYard;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

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
  @RestResource(path = "byOrderId")
  List<ProcurementMaterial> findAllByOrderId(@Param("orderId") Long orderId);

  /**
   * 根据订单id查询与需求物料关联的待采购物料.
   *
   * @param orderId 订单id
   * @return .
   */
  @RestResource(path = "byOrderIdAndPlanMaterialIdIsNotNull")
  List<ProcurementMaterial> findAllByOrderIdAndPlanMaterialIdIsNotNull(
      @Param("orderId") Long orderId);
}