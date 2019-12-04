package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/4 下午6:53
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserDetailsServiceTest {

  @Autowired
  private SysUserRepository sysUserRepository;
  private UserDetailsService userDetailsService;

  @BeforeEach
  void setUp() {
    userDetailsService = new UserDetailsServiceImpl(sysUserRepository);
  }

  @Test
  @Tag("正常")
  @DisplayName("加载用户信息")
  void loadUserByUsername() {
    UserDetails admin = userDetailsService.loadUserByUsername("admin");
    assertNotNull(admin);
  }

  @Test
  @Tag("失败")
  @DisplayName("加载用户信息用户名不存在")
  void loadUserByUsernameUsernameNotFoundException() {
    assertThrows(UsernameNotFoundException.class,
        () -> userDetailsService.loadUserByUsername("Test"));
  }
}