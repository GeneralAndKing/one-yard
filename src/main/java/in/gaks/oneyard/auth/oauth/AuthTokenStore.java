package in.gaks.oneyard.auth.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
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
   * 声明 redis TokenStore实现.
   *
   * @return JdbcTokenStore
   */
  @Bean
  public TokenStore redisTokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

  /**
   * jwt 转换.
   *
   * @return 结果
   */
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("g&k");
    return converter;
  }


  /**
   * jwt token store.
   *
   * @return JwtTokenStore
   */
  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }


}
