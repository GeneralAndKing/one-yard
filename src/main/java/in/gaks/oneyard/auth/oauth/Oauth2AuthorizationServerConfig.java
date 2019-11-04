package in.gaks.oneyard.auth.oauth;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 授权服务器配置.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午4:51
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;
  private final TokenStore redisTokenStore;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("gak")
        .secret("$2a$12$cLJL7vsJLzM07g83pV7FBeosMxwi0TWm1N70GskkmG2CIcWCFbLYK")
        .authorizedGrantTypes("refresh_token", "password")
        .resourceIds("gak")
        .scopes("all")
        .accessTokenValiditySeconds(Math.toIntExact(Duration.ofDays(1).getSeconds()))
        .refreshTokenValiditySeconds(Math.toIntExact(Duration.ofDays(1).getSeconds()));
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.authenticationManager(this.authenticationManager)
        .tokenStore(redisTokenStore);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security
        .tokenKeyAccess("isAuthenticated()")
        .checkTokenAccess("isAuthenticated()");
  }

}
