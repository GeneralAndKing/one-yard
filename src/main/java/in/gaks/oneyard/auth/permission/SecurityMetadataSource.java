package in.gaks.oneyard.auth.permission;


import static in.gaks.oneyard.model.helper.SecurityConstants.ROLE_ADMIN;
import static in.gaks.oneyard.model.helper.SecurityConstants.ROLE_NO_AUTH;
import static in.gaks.oneyard.model.helper.SecurityConstants.ROLE_PUBLIC;

import in.gaks.oneyard.model.constant.HttpMethod;
import in.gaks.oneyard.model.entity.SysPermission;
import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
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
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午10:02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  private final SysPermissionRepository sysPermissionRepository;
  private final SysRoleRepository sysRoleRepository;
  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    HttpServletRequest httpRequest = ((FilterInvocation) object).getHttpRequest();
    if (isRoleAdmin()) {
      return SecurityConfig.createList(ROLE_PUBLIC);
    }
    String method = httpRequest.getMethod();
    String requestUrl = httpRequest.getServletPath();
    List<SysPermission> permissions = sysPermissionRepository.findAll();
    SysRole publicRole = sysRoleRepository.findFirstByName(ROLE_PUBLIC).orElseThrow(
        () -> new ResourceNotFoundException("公共资源角色未找到！"));
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
      Set<SysRole> roles = permission.getRoles();
      if (CollectionUtils.isEmpty(roles)) {
        continue;
      }
      // 获取匹配当前资源的角色 id 表
      List<Long> sysRoleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
      // 如果当前匹配的角色中含有公共资源的 id
      if (sysRoleIds.contains(publicRole.getId())) {
        return SecurityConfig.createList(ROLE_PUBLIC);
      }
      List<SysRole> matchRoles = roles.stream().filter(
          sysRole -> sysRoleIds.contains(sysRole.getId())).collect(Collectors.toList());
      if (CollectionUtils.isEmpty(matchRoles)) {
        continue;
      }
      return SecurityConfig.createListFromCommaDelimitedString(matchRoles.stream()
          .map(SysRole::getName).collect(Collectors.joining(",")));
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
