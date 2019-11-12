package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.SysDepartment;
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
   * 通过角色 id  查询.
   *
   * @param ids ids
   * @return 结果
   */
  @RestResource(path = "byRoles")
  @Query(value = "select d.* from sys_role r, sys_department d "
      + "where r.id in (:ids) and r.department_id = d.id",
      nativeQuery = true)
  List<SysDepartment> searchByRoles(@Param("ids") List<Long> ids);

}
