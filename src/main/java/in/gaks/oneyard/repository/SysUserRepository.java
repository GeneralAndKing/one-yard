package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.constant.OneYard;
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
@RepositoryRestResource(path = OneYard.SYS_USER)
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

  /**
   * 根据角色id查询用户.
   *
   * @param id id
   * @return 结果
   */
  @RestResource(path = "byRole")
  @Query(value = "select u.* from sys_user u, sys_role r "
      + "where r.id = :id and u.role_id = r.id "
      + "and u.is_enable = 1 and r.is_enable = 1",
      nativeQuery = true)
  List<SysUser> searchByRoleId(@Param("id") Long id);

  /**
   * 通过名字查询是否存在.
   *
   * @param name 名字
   * @return 结果
   */
  boolean existsByName(String name);

  /**
   * 通过用户名查询是否存在.
   *
   * @param username 用户名
   * @return 结果
   */
  boolean existsByUsername(String username);

  /**
   * 通过邮箱查询是否存在.
   *
   * @param email 邮箱
   * @return 结果
   */
  boolean existsByEmail(String email);

  /**
   * 通过手机号查询是否存在.
   *
   * @param phone 手机号
   * @return 结果
   */
  boolean existsByPhone(String phone);

  /**
   * 根据邮箱查询用户.
   *
   * @param email 邮箱
   * @return 用户
   */
  @RestResource(path = "byEmail")
  Optional<SysUser> findFirstByEmail(String email);

  /**
   * 根据邮箱和名称查询用户.
   *
   * @param name  名称
   * @param email 邮箱
   * @return 用户
   */
  @RestResource(path = "byNameOrEmail")
  Optional<SysUser> findFirstByUsernameOrEmail(String name, String email);

  /**
   * 根据邮箱和名称和手机号查询用户.
   *
   * @param name  名称
   * @param email 邮箱
   * @param phone 手机号
   * @return 用户
   */
  @RestResource(path = "byNameOrEmail")
  Optional<SysUser> findFirstByUsernameOrEmailOrPhone(String name, String email, String phone);

}
