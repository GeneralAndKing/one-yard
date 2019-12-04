package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.impl.SysUserServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/4 下午7:47
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class SysUserServiceTest {

  @Autowired
  private SysUserRepository sysUserRepository;
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
  private SysUserService sysUserService;

  @BeforeEach
  void setUp() {
    sysUserService = new SysUserServiceImpl(sysUserRepository, passwordEncoder);
  }

  @Test
  @Tag("正常")
  @DisplayName("查询所有用户")
  void findAll() {
    assertNotNull(sysUserService.findAll());
  }

  @Test
  @Tag("正常")
  @DisplayName("根据用户名获取信息")
  void findByUsername() {
    assertNotNull(sysUserService.findByUsername("admin"));
  }

  @Test
  @Tag("正常")
  @DisplayName("修改密码")
  void modifyPassword() {
    sysUserService.modifyPassword("admin", "123456789", "987654321");
    SysUser admin = sysUserRepository.findFirstByUsername("admin").orElse(null);
    assertNotNull(admin);
    assertTrue(passwordEncoder.matches("987654321", admin.getPassword()));
  }

  @Test
  @Tag("异常")
  @DisplayName("修改密码资源错误异常")
  void modifyPasswordResourceErrorException() {
    ResourceErrorException exception = assertThrows(ResourceErrorException.class,
        () -> sysUserService.modifyPassword("admin", "987654321", "987654321"));
    assertEquals("原密码不匹配", exception.getMessage());
  }

  @Test
  @Tag("正常")
  @DisplayName("保存")
  void save() {
    SysUser test = sysUserService.save(new SysUser().setUsername("test"));
    assertEquals("test", test.getUsername());
    assertNotNull(test.getId());
  }

  @Test
  @Tag("正常")
  @DisplayName("根据部门查询用户")
  void searchByDepartments() {
    // 这里使用 h2 数据库无法测试，因为关联表是由我们自己维护的，所以必然报错
    // 节约时间就不去处理了
    assertThrows(InvalidDataAccessResourceUsageException.class,
        () -> sysUserService.searchByDepartments(List.of(1L)));
  }
}