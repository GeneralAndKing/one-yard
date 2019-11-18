package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.SysRole;
import java.util.List;
import java.util.Optional;
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
  @Query(value = "select r.* from sys_role r, sys_department d "
      + "where d.id = :id and r.department_id = d.id and r.is_enable = 1 and d.is_enable = 1",
      nativeQuery = true)
  List<SysRole> searchByDepartmentId(@Param("id") Long id);

  /**
   * 根据部门号查询角色.
   *
   * @param ids 部门号
   * @return 角色
   */
  @RestResource(path = "byDepartments")
  @Query(value = "select r.* from sys_role r, sys_department d "
      + "where d.id in (:ids) and r.department_id = d.id "
      + "and r.is_enable = 1 and d.is_enable = 1",
      nativeQuery = true)
  List<SysRole> searchByDepartmentIds(@Param("ids") List<Long> ids);

  /**
   * 根据用户查询角色.
   *
   * @param id 用户
   * @return 角色
   */
  @RestResource(path = "byUser")
  @Query(value = "select r.* from sys_role r, sys_user_role ur, sys_user u "
      + "where u.id = :id and ur.user_id = u.id and ur.role_id = r.id "
      + "and r.is_enable = 1 and ur.is_enable = 1 and u.is_enable = 1",
      nativeQuery = true)
  List<SysRole> searchByUser(@Param("id") Long id);

  /**
   * 根据权限查询角色.
   *
   * @param id id
   * @return 角色
   */
  @RestResource(path = "byPermission")
  @Query(value = "select r.* from sys_role r, sys_role_permission rp, sys_permission p "
      + "where p.id = :id and rp.permission_id = p.id and rp.role_id = r.id "
      + "and r.is_enable = 1 and rp.is_enable = 1 and p.is_enable = 1",
      nativeQuery = true)
  List<SysRole> searchByPermission(@Param("id") Long id);


  /**
   * 根据名称查询角色.
   *
   * @param name 名称
   * @return 角色
   */
  @RestResource(exported = false)
  Optional<SysRole> findFirstByName(String name);
}
