package in.gaks.oneyard.auth.oauth;

import in.gaks.oneyard.auth.permission.AuthAccessDecisionManager;
import in.gaks.oneyard.config.ApiFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午5:23
 */
@Configuration
@RequiredArgsConstructor
@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private final TokenStore redisTokenStore;
  private final FilterInvocationSecurityMetadataSource securityMetadataSource;
  private final AuthAccessDecisionManager authAccessDecisionManager;
  private final ApiFilter apiFilter;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    // 设置资源服务器的 id
    resources.resourceId("gak")
        .tokenStore(redisTokenStore);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.addFilterBefore(apiFilter, AbstractPreAuthenticatedProcessingFilter.class);
    http
        .authorizeRequests()
        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
          @Override
          public <O extends FilterSecurityInterceptor> O postProcess(O o) {
            o.setSecurityMetadataSource(securityMetadataSource);
            o.setAccessDecisionManager(authAccessDecisionManager);
            return o;
          }
        })
        .anyRequest()
        .authenticated()
        .and()
        .cors()
        .and()
        .csrf()
        .disable();
  }


}
