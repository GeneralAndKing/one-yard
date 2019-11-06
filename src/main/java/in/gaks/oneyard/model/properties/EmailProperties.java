package in.gaks.oneyard.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮箱配置.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午3:32
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class EmailProperties {

  /**
   * host.
   */
  private String host;
  /**
   * port.
   */
  private String port;
  /**
   * username.
   */
  private String username;
  /**
   * password.
   */
  private String password;
}
