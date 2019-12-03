package in.gaks.oneyard;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.repository.SysUserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@DataJpaTest
@ExtendWith(SpringExtension.class)
class OneYardApplicationTests {

  @Autowired
  private SysUserRepository sysUserRepository;

  @Test
  @DisplayName("测试 H2 数据库启动")
  void contextLoads() {
    List<SysUser> all = sysUserRepository.findAll();
    assertNotNull(all);
  }

}
