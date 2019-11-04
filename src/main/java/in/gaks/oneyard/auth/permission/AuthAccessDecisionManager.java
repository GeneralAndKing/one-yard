package in.gaks.oneyard.auth.permission;

import static in.gaks.oneyard.model.helper.SecurityConstants.ROLE_NO_AUTH;
import static in.gaks.oneyard.model.helper.SecurityConstants.ROLE_PUBLIC;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午9:58
 */
@Slf4j
@Component
public class AuthAccessDecisionManager implements AccessDecisionManager {
  @Override
  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
    for (ConfigAttribute configAttribute : configAttributes) {
      String needRole = configAttribute.getAttribute();
      //  对于不允许访问的资源
      if (ROLE_NO_AUTH.equals(needRole)) {
        throw new AccessDeniedException("权限不足");
      }
      // 公共资源或者通过的资源
      if (ROLE_PUBLIC.equals(needRole) || authentication.getAuthorities().stream().anyMatch(
          authority -> authority.getAuthority().equals(needRole))) {
        return;
      }
    }
    throw new AccessDeniedException("权限不足");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }
}
