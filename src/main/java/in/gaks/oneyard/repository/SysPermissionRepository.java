package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysPermission;
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
@RepositoryRestResource(path = OneYard.SYS_PERMISSION)
public interface SysPermissionRepository extends BaseRepository<SysPermission, Long> {

  /**
   * 通过角色查询.
   *
   * @param id id
   * @return 结果
   */
  @RestResource(path = "byRole")
  @Query(value = "select p.* from sys_permission p, sys_role_permission rp, sys_role r "
      + "where r.id = :id and rp.role_id = r.id and rp.permission_id = p.id"
      + "  and r.is_enable = 1 and rp.is_enable = 1 and p.is_enable = 1",
      nativeQuery = true)
  List<SysPermission> searchByRole(Long id);


  /**
   * 通过部门查询.
   *
   * @param id id
   * @return 结果
   */
  @RestResource(path = "byDepartment")
  @Query(value =
      "select p.* from sys_permission p, sys_department_permission dp, sys_department d\n"
          + "where d.id = :id and dp.department_id = d.id and dp.permission_id = p.id"
          + "  and d.is_enable = 1 and dp.is_enable = 1 and p.is_enable = 1",
      nativeQuery = true)
  List<SysPermission> searchByDepartment(Long id);
}
