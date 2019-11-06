package in.gaks.oneyard.model.properties;

import java.time.Duration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/5 下午4:31
 */
@Data
@Component
@ConfigurationProperties("application.security")
public class SecurityProperties {

  private Boolean enable = true;

  private String clientId;

  private String clientSecret;

  private Long accessTokenValidityHour = 1L;

  private Long refreshTokenValidityHour = 1L;

  public int accessTokenValiditySeconds() {
    return Math.toIntExact(Duration.ofHours(accessTokenValidityHour).getSeconds());
  }

  public int refreshTokenValiditySeconds() {
    return Math.toIntExact(Duration.ofHours(refreshTokenValidityHour).getSeconds());
  }

}
