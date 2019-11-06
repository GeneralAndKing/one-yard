package in.gaks.oneyard.auth.permission;

import static in.gaks.oneyard.model.constant.SecurityConstants.ROLE_ADMIN;
import static in.gaks.oneyard.model.constant.SecurityConstants.ROLE_NO_AUTH;
import static in.gaks.oneyard.model.constant.SecurityConstants.ROLE_PUBLIC;

import in.gaks.oneyard.model.constant.HttpMethod;
import in.gaks.oneyard.model.entity.SysPermission;
import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.properties.SecurityProperties;
import in.gaks.oneyard.repository.SysPermissionRepository;
import in.gaks.oneyard.repository.SysRoleRepository;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;


/**
 * FilterInvocationSecurityMetadataSource.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午10:02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  private final SysRoleRepository sysRoleRepository;
  private final SysPermissionRepository sysPermissionRepository;
  private final SecurityProperties securityProperties;
  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
    if (isRoleAdmin() || !securityProperties.getEnable()) {
      return SecurityConfig.createList(ROLE_PUBLIC);
    }
    String method = httpRequest.getMethod();
    String requestUrl = httpRequest.getServletPath();
    List<SysPermission> permissions = sysPermissionRepository.findAll();
    for (SysPermission permission : permissions) {
      // 路径匹配
      if (!antPathMatcher.match(permission.getMatchUrl(), requestUrl)) {
        continue;
      }
      // 方法匹配
      if (!HttpMethod.ALL.match(permission.getMethod())
          && !(method.equals(permission.getMethod().name()))) {
        continue;
      }
      List<SysRole> roles = sysRoleRepository.searchByPermission(permission.getId());
      if (CollectionUtils.isEmpty(roles)) {
        continue;
      }
      Set<String> roleNames = roles.stream().map(SysRole::getName).collect(Collectors.toSet());
      // 如果当前匹配的角色中含有公共角色
      if (roleNames.contains(ROLE_PUBLIC)) {
        return SecurityConfig.createList(ROLE_PUBLIC);
      }
      return SecurityConfig.createListFromCommaDelimitedString(
          String.join(",", roleNames));
    }
    return SecurityConfig.createList(ROLE_NO_AUTH);
  }

  private boolean isRoleAdmin() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        .contains(new SimpleGrantedAuthority(ROLE_ADMIN));
  }


  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }
}
