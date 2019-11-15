package in.gaks.oneyard.service.impl;

import static in.gaks.oneyard.model.constant.SecurityConstants.ROLE_ACCESS;

import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceException;
import in.gaks.oneyard.model.exception.ResourceExistException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.SysRoleRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.AuthService;
import in.gaks.oneyard.util.AvatarUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/5 下午6:07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final SysUserRepository sysUserRepository;
  private final SysRoleRepository sysRoleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void register(SysUser sysUser) {
    Optional<SysUser> exist = sysUserRepository.findFirstByUsernameOrEmailOrPhone(
        sysUser.getUsername(), sysUser.getEmail(), sysUser.getPhone());
    if (exist.isPresent()) {
      throw new ResourceExistException("用户已存在");
    }
    SysRole access = sysRoleRepository.findFirstByName(ROLE_ACCESS).orElseThrow(
        () -> new ResourceException("系统角色 %s 未找到，请确认是否拥有此角色！", ROLE_ACCESS)
    );
    sysUser.setId(null);
    List<SysRole> roles = new ArrayList<>(1);
    roles.add(access);
    sysUser.setRoles(roles);
    sysUser.setIcon(AvatarUtil.avatar());
    sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
    SysUser save = sysUserRepository.save(sysUser);
    Assert.notNull(save.getId(), "注册失败");
  }

  @Override
  public boolean existByEmail(String email) {
    return sysUserRepository.findFirstByEmail(email).isPresent();
  }

  @Override
  public void modifyPassword(String email, String password) {
    SysUser sysUser = sysUserRepository.findFirstByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("用户 %s 不存在", email));
    sysUser.setPassword(passwordEncoder.encode(password));
    sysUserRepository.save(sysUser);
  }

}
