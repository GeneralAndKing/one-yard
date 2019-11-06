package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.model.constant.Status;
import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.repository.SysRoleRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午5:04
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final SysUserRepository sysUserRepository;
  private final SysRoleRepository sysRoleRepository;

  /**
   * 通过用户名查找用户，这是对密码登录的仅有支持.
   *
   * <p>
   * 在对应的容器中，他会自己调用 {@link UserDetailsService} 接口实现
   * </p>
   *
   * @param username 用户名
   * @return 用户信息
   * @throws UsernameNotFoundException 用户位置哦到
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("email login user: {}", username);
    SysUser sysUser = sysUserRepository.findFirstByUsernameOrEmail(username, username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("用户 %s 不存在", username)));
    List<SysRole> sysRoles = sysRoleRepository.searchByUser(sysUser.getId());
    List<SimpleGrantedAuthority> authorities = sysRoles.stream()
        .map(sysRole -> new SimpleGrantedAuthority(sysRole.getName()))
        .collect(Collectors.toList());
    return new User(sysUser.getName(), sysUser.getPassword(), sysUser.getIsEnable(), true, true,
        !Status.isNormal(sysUser.getStatus()), authorities);
  }

}
