package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.service.SysUserService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户接口.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:31
 */
@Slf4j
@RequiredArgsConstructor
@RepositoryRestController
@RequestMapping(OneYard.SYS_USER)
public class SysUserController extends BaseController<SysUser, SysUserService, Long> {

  private final SysUserService sysUserService;

  /**
   * 获取所有.
   *
   * @return 结果
   */
  @GetMapping("all")
  public HttpEntity<?> all() {
    List<SysUser> all = sysUserService.findAll();
    return ResponseEntity.ok(all.stream()
        .map(user -> user.setPassword("xxxxxxxxxxxxxxxxxx"))
        .collect(Collectors.toList()));
  }

  /**
   * 获取当前用户的信息.
   *
   * @param principal 登录授权
   * @return 响应
   */
  @GetMapping("me")
  @PreAuthorize("isFullyAuthenticated()")
  public HttpEntity<?> me(Principal principal) {
    JSONObject me = new JSONObject();
    me.put("principal", principal);
    me.put("me", sysUserService.findByUsername(principal.getName()));
    return ResponseEntity.ok(me);
  }
}
