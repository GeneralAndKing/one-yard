package in.gaks.oneyard;

import static org.junit.jupiter.api.Assertions.assertFalse;

import in.gaks.oneyard.repository.SysRoleRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.AuthService;
import in.gaks.oneyard.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@DataJpaTest
@ExtendWith(SpringExtension.class)
class OneYardApplicationTests {

  @Autowired
  private SysUserRepository sysUserRepository;
  @Autowired
  private SysRoleRepository sysRoleRepository;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private AuthService authService;

  @BeforeEach
  public void setUp() {
    authService = new AuthServiceImpl(sysUserRepository, sysRoleRepository, passwordEncoder);
  }

  @Test
  @DisplayName("测试 H2 数据库启动")
  void contextLoads() {
    boolean b = authService.existByEmail("lizhongyue248@163.com");
    assertFalse(b);
  }

}
