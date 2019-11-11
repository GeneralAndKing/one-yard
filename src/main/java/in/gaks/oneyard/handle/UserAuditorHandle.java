package in.gaks.oneyard.handle;

import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户自动装配.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/9 下午9:35
 */
@Configuration
public class UserAuditorHandle implements AuditorAware<String> {

  private static final String ANONYMOUS_USER = "anonymousUser";

  /**
   * 获取用户 username.
   *
   * @return 用户 username
   */
  @NotNull
  @Override
  public Optional<String> getCurrentAuditor() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (Objects.isNull(context)
        || Objects.isNull(context.getAuthentication())
        || Objects.isNull(context.getAuthentication().getPrincipal())) {
      return Optional.of(ANONYMOUS_USER);
    }
    Object principal = context.getAuthentication().getPrincipal();
    if (principal.getClass().isAssignableFrom(String.class)) {
      return Optional.of(principal.toString());
    }
    return Optional.of(ANONYMOUS_USER);
  }
}
