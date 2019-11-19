package in.gaks.oneyard.config;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/18 下午10:52
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiFilter extends OncePerRequestFilter {

  private final RedisTemplate<String, String> redisTemplate;
  private AntPathMatcher antPathMatcher = new AntPathMatcher();
  private Set<String> authUrls = Sets.newHashSet("/api/oauth/token", "/api/oauth/check_token");

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    ListOperations<String, String> operation = redisTemplate.opsForList();
    operation.leftPush("api-num=" + LocalDate.now().toString(), request.getRequestURL().toString());
    String uri = request.getRequestURI();
    authUrls.forEach(auth -> {
      if (antPathMatcher.match(auth, uri)) {
        operation
            .leftPush("auth-num=" + LocalDate.now().toString(), request.getRequestURL().toString());
      }
    });
    filterChain.doFilter(request, response);
  }
}
