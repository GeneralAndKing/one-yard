package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysDepartment;
import in.gaks.oneyard.model.helper.OneYard;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:26
 */
@RepositoryRestResource(path = OneYard.SYS_DEPARTMENT)
public interface SysDepartmentRepository extends BaseRepository<SysDepartment, Long> {

  /**
   * 根据权限查询部门.
   *
   * @param id id
   * @return 部门
   */
  @RestResource(path = "byPermission")
  @Query(value = "select d.* from sys_department d, sys_department_permission dp, sys_permission p "
      + "where p.id = :id and dp.permission_id = p.id and dp.department_id = d.id "
      + "and d.is_enable = 1 and dp.is_enable = 1 and p.is_enable = 1",
      nativeQuery = true)
  List<SysDepartment> searchByPermission(@Param("id") Long id);

}
