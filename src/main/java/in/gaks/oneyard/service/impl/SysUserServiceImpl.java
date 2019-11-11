package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.SysUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

  @Override
  public List<SysUser> findAll() {
    return sysUserRepository.findAll();
  }

  @Override
  public SysUser findByUsername(String username) {
    return sysUserRepository.findFirstByUsername(username).orElse(null);
  }
}
