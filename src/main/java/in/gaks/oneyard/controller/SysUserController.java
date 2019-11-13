package in.gaks.oneyard.controller;

import static in.gaks.oneyard.model.constant.SecurityConstants.ANONYMOUS_USER;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.service.SysUserService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  private final @NonNull SysUserService sysUserService;

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

  /**
   * 修改密码.
   *
   * @param param     接受的参数
   * @param principal 用户信息
   * @return 结果
   */
  @PostMapping("password/modify")
  @PreAuthorize("isFullyAuthenticated()")
  public HttpEntity<?> modifyPassword(@RequestBody JSONObject param, Principal principal) {
    String oldPassword = param.getString("oldPassword");
    String newPassword = param.getString("newPassword");
    String rePassword = param.getString("rePassword");
    Assert.notNull(oldPassword, "旧密码不能为空");
    Assert.notNull(newPassword, "新密码不能为空");
    Assert.notNull(rePassword, "重复密码不能为空");
    Assert.isTrue(newPassword.equals(rePassword), "两次密码不一致");
    String name = principal.getName();
    Assert.notNull(name, "无法获取当前用户信息");
    Assert.isTrue(!name.equals(ANONYMOUS_USER), "当前用户是匿名用户，无权进行此操作");
    sysUserService.modifyPassword(name, oldPassword, newPassword);
    return ResponseEntity.noContent().build();
  }

}
