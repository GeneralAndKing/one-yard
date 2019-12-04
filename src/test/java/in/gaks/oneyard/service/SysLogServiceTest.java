package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.model.entity.SysLog;
import in.gaks.oneyard.repository.SysLogRepository;
import in.gaks.oneyard.service.impl.SysLogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/4 下午6:39
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class SysLogServiceTest {

  @Autowired
  private SysLogRepository sysLogRepository;
  private SysLogService sysLogService;

  @BeforeEach
  void setUp() {
    sysLogService = new SysLogServiceImpl(sysLogRepository);
  }

  @Test
  @Tag("正常")
  @DisplayName("日志异步存储")
  void asyncSave() {
    SysLog test = new SysLog().setArgs("test");
    sysLogService.asyncSave(test);
    assertNotNull(test.getId());
  }
}