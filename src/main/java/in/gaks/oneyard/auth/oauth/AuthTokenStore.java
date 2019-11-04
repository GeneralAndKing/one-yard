package in.gaks.oneyard.auth.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午11:08
 */
@Configuration
@RequiredArgsConstructor
public class AuthTokenStore {

  private final RedisConnectionFactory redisConnectionFactory;

  /**
   * TokenServices
   *
   * @return Token 配置
   */
  @Bean
  @Primary
  public DefaultTokenServices defaultTokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(redisTokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  /**
   * 声明 redis TokenStore实现
   *
   * @return JdbcTokenStore
   */
  @Bean
  @Primary
  public TokenStore redisTokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }


}
