package in.gaks.oneyard.controller;

import in.gaks.oneyard.base.BaseController;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.service.SysUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
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

}
