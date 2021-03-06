package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.MaterialType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.MATERIAL_TYPE)
public interface MaterialTypeRepository extends BaseRepository<MaterialType, Long> {

  /**
   * 查询是否 code 存在.
   *
   * @param code code
   * @return 结果
   */
  boolean existsByCode(@Param("code") String code);

}