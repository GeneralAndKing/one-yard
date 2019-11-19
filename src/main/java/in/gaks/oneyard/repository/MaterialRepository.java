package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.Material;
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
@SuppressWarnings("unused")
@RepositoryRestResource(path = OneYard.MATERIAL)
public interface MaterialRepository extends BaseRepository<Material, Long> {

  /**
   * 通过类型 id 查.
   *
   * @param typeId 类型
   * @return 结果
   */
  @RestResource(path = "byTypeId")
  List<Material> findByTypeId(@Param("typeId") Long typeId);

  /**
   * 查询是否 code 存在.
   *
   * @param code code
   * @return 结果
   */
  boolean existsByCode(@Param("code") String code);

  /**
   * 查询数量.
   *
   * @return 结果
   */
  @RestResource(path = "count")
  long countByCodeNotNull();
}