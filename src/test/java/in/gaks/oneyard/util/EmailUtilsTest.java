package in.gaks.oneyard.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午3:46
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class EmailUtilsTest {

  @Autowired
  private EmailUtils emailUtils;

  @Test
  void sendSimpleMail() throws Exception {
//    emailUtils.sendSimpleMail("lizhongyue248@163.com", "测试", "欢迎使用");
//    Thread.sleep(500000);
  }

  @Test
  void sendTemplateMail() throws Exception {
//    Map<String, Object> map = new HashMap<>();
//    map.put("time", 100);
//    map.put("type", "注册邮件");
//    map.put("code", "test");
//    emailUtils.sendTemplateMail("lizhongyue248@163.com", "注册",
//        "测试", "CodeTemplate.html", map);
//    Thread.sleep(500000);
  }
}