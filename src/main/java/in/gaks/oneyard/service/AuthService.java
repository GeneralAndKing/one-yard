package in.gaks.oneyard.service;

import in.gaks.oneyard.model.entity.SysUser;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/5 下午6:07
 */
public interface AuthService {

  /**
   * 校验是否存在用户.
   *
   * @param sysUser 用户
   * @return 结果
   */
  void register(SysUser sysUser);

  /**
   * 通过邮箱检验是否存在用户.
   *
   * @param email email
   * @return result
   */
  boolean existByEmail(String email);

  /**
   * 修改密码.
   *
   * @param email    邮箱
   * @param password 密码
   */
  void modifyPassword(String email, String password);
}
