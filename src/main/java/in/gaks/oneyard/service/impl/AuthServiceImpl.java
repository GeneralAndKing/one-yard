package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceExistException;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.AuthService;
import java.util.Optional;
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
  private final PasswordEncoder passwordEncoder;

  @Override
  public void register(SysUser sysUser) {
    Optional<SysUser> exist = sysUserRepository.findFirstByUsernameOrEmailOrPhone(
        sysUser.getUsername(), sysUser.getEmail(), sysUser.getPhone());
    if (exist.isPresent()) {
      throw new ResourceExistException("用户已存在");
    }
    sysUser.setId(null);
    sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
    SysUser save = sysUserRepository.save(sysUser);
    Assert.notNull(save.getId(), "注册失败");
  }

  @Override
  public boolean existByEmail(String email) {
    return sysUserRepository.findFirstByEmail(email).isPresent();
  }
}
