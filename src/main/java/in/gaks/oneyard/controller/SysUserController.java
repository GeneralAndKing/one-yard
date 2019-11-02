package in.gaks.oneyard.controller;

import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.SysRoleRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.util.CommonUtil;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * 用户接口.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:31
 */
@Slf4j
@Controller
@GraphQLApi
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SysUserController implements BaseController {

  private final SysUserRepository sysUserRepository;
  private final SysRoleRepository sysRoleRepository;

  /**
   * 根据条件查询.
   *
   * @param sysUser 条件，为空表示全查
   * @return 结果
   */
  @GraphQLQuery
  public List<SysUser> sysUsers(SysUser sysUser) {
    return sysUserRepository.findAll();
  }

  /**
   * 根据 id 查询单个.
   *
   * @param id id
   * @return 一个
   */
  @GraphQLQuery
  public SysUser sysUser(@GraphQLNonNull Long id) {
    return sysUserRepository.findById(id).orElse(null);
  }

  /**
   * 创建用户.
   *
   * @param sysUser 用户
   * @return 创建后的
   */
  @GraphQLMutation
  public SysUser createSysUser(@GraphQLNonNull SysUser sysUser) {
    return sysUserRepository.save(sysUser);
  }

  /**
   * 更新用户.
   *
   * @param id 需要修改的用户的 id
   * @param sysUser 修改后用户或者要修改的字段
   * @return 结果
   */
  @GraphQLMutation
  public SysUser updateSysUser(@GraphQLNonNull Long id, @GraphQLNonNull SysUser sysUser) {
    SysUser exist = sysUserRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException(String.format("SysUser Not Found By Id %s.", id)));
    CommonUtil.copyNonNullProperties(sysUser, exist);
    return sysUserRepository.save(exist);
  }

  /**
   * 联查.
   *
   * @param sysUser 用户
   * @return 相关联的角色
   */
  @GraphQLQuery(name = "roles")
  public SysRole sysRoleBySysUser(@GraphQLContext SysUser sysUser) {
    @NonNull Long roleId = sysUser.getRoleId();
    return sysRoleRepository.findById(roleId).orElse(null);
  }

}
