package in.gaks.oneyard.handle;

import in.gaks.oneyard.model.entity.SysUser;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/8 上午12:07
 */
@Component
@SuppressWarnings("unused")
@RepositoryEventHandler
@RequiredArgsConstructor
public class SysUserEventHandler {

  private final PasswordEncoder passwordEncoder;

  /**
   * 保存用户之前，对密码进行加密.
   *
   * @param user 用户
   */
  @HandleBeforeSave
  public void handleSysUserSave(@NotNull SysUser user) {
    if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
  }

  /**
   * 创建用户之前，对密码进行加密.
   *
   * @param user 用户
   */
  @HandleBeforeCreate
  public void handleSysUserCreate(@NotNull SysUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

}
