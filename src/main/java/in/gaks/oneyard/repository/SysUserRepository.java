package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysUser;
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
@RepositoryRestResource(path = OneYard.SYS_USER)
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

  /**
   * 根据角色id查询用户.
   *
   * @param id id
   * @return 结果
   */
  @RestResource(path = "byRole")
  @Query(value = "select u.* from sys_user u, sys_role r, sys_user_role ur "
      + "where u.id = ur.user_id and ur.role_id = r.id and u.is_enable = 1\n"
      + "  and ur.is_enable = 1 and r.is_enable = 1 and r.id = :id",
      nativeQuery = true)
  List<SysUser> searchByRoleId(Long id);

}
