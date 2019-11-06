package in.gaks.oneyard.model.helper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午1:29
 */
@Data
@Component
@ConfigurationProperties("application")
public class ApplicationProperties {

  private String registerCodeKeySuffix = "@register";

  private Integer registerCodeLength = 4;

  private Long registerCodeValidityMinute = 1L;

  private Long forgetCodeValidityMinute = 15L;

  public String registerKey(String email) {
    return email + registerCodeKeySuffix;
  }

}
