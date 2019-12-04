package in.gaks.oneyard.service;

import static in.gaks.oneyard.model.constant.SecurityConstants.ROLE_ACCESS;
import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceException;
import in.gaks.oneyard.model.exception.ResourceExistException;
import in.gaks.oneyard.repository.SysRoleRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/4 下午5:08
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class AuthServiceTest {

  @Autowired
  private SysUserRepository sysUserRepository;
  @Autowired
  private SysRoleRepository sysRoleRepository;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
  private AuthService authService;

  @BeforeEach
  void setUp() {
    authService = new AuthServiceImpl(sysUserRepository, sysRoleRepository, passwordEncoder);
  }

  @Test
  @Tag("正常")
  @Order(1)
  @DisplayName("注册成功")
  void register() {
    SysUser user = new SysUser().setUsername("test").setEmail("test").setPhone("test")
        .setPassword("123456");
    authService.register(user);
  }

  @Test
  @Tag("失败")
  @Order(2)
  @DisplayName("注册用户存在异常")
  void registerResourceExistException() {
    SysUser user = new SysUser().setUsername("admin");
    ResourceExistException exception = assertThrows(ResourceExistException.class,
        () -> authService.register(user));
    assertEquals("用户已存在", exception.getMessage());
  }

  @Test
  @Tag("失败")
  @Order(3)
  @DisplayName("注册角色失败存在异常")
  void registerResourceException() {
    SysRole access = sysRoleRepository.findFirstByName(ROLE_ACCESS).orElseThrow(
        () -> new ResourceException("系统角色 %s 未找到，请确认是否拥有此角色！", ROLE_ACCESS)
    );
    sysRoleRepository.delete(access);
    SysUser user = new SysUser().setUsername("test1");
    ResourceException exception = assertThrows(ResourceException.class,
        () -> authService.register(user));
    assertEquals("系统角色 ROLE_ACCESS 未找到，请确认是否拥有此角色！", exception.getMessage());
  }

  @Test
  @Tag("正常")
  @DisplayName("根据邮箱判断是否存在")
  void existByEmail() {
    assertTrue(authService.existByEmail("123456@163.com"));
  }

  @Test
  @Tag("正常")
  @DisplayName("修改密码")
  void modifyPassword() {
    authService.modifyPassword("123456@163.com", "987654321");
    SysUser one = sysUserRepository.getOne(1L);
    assertTrue(passwordEncoder.matches("987654321", one.getPassword()));
  }
}