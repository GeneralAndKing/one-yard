package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.SysUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserRepository, SysUser, Long>
    implements SysUserService {

  private final SysUserRepository sysUserRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<SysUser> findAll() {
    return sysUserRepository.findAll();
  }

  @Override
  public SysUser findByUsername(String username) {
    return sysUserRepository.findFirstByUsername(username).orElse(null);
  }

  @Override
  public void modifyPassword(String name, String oldPassword, String newPassword) {
    SysUser user = sysUserRepository.findFirstByUsername(name)
        .orElseThrow(() -> new ResourceNotFoundException("用户资源未找到"));
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
      throw new ResourceErrorException("原密码不匹配");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    sysUserRepository.save(user);
  }

  @Override
  public SysUser save(SysUser sysUser) {
    return sysUserRepository.save(sysUser);
  }
}
