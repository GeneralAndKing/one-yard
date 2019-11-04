package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.helper.OneYard;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:26
 */
@SuppressWarnings("unused")
@RepositoryRestResource(path = OneYard.SYS_ROLE)
public interface SysRoleRepository extends BaseRepository<SysRole, Long> {

  /**
   * 根据部门号查询角色.
   *
   * @param id 部门号
   * @return 角色
   */
  @RestResource(path = "byDepartment")
  @Query(value = "select r.* from sys_role r, sys_department d\n"
      + "where d.id = :id and r.department_id = d.id and r.is_enable = 1 and d.is_enable = 1",
      nativeQuery = true)
  List<SysRole> searchByDepartmentId(Long id);

}
