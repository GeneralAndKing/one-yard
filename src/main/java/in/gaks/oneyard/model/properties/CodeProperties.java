package in.gaks.oneyard.model.properties;

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
@ConfigurationProperties("application.code")
public class CodeProperties {

  private String registerCodeKeySuffix = "@register";

  private String forgetCodeKeySuffix = "@forget";

  private Integer emailCodeLength = 4;

  private Long emailCodeValidityMinute = 15L;

  public String registerKey(String email) {
    return email + registerCodeKeySuffix;
  }

  public String forgetKey(String email) {
    return email + forgetCodeKeySuffix;
  }

}
