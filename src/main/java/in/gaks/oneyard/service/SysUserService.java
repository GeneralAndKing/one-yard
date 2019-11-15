package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.SysUser;
import java.util.List;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午11:00
 */
public interface SysUserService extends BaseService<SysUser, Long> {

  /**
   * 查所有.
   *
   * @return all
   */
  List<SysUser> findAll();

  /**
   * 根据用户名获取信息.
   *
   * @param username 用户名
   * @return 结果
   */
  SysUser findByUsername(String username);

  /**
   * 修改密码.
   *
   * @param name        用户名
   * @param oldPassword 旧密码
   * @param newPassword 新密码
   */
  void modifyPassword(String name, String oldPassword, String newPassword);

  /**
   * 保存/更新用户.
   *
   * @param sysUser 用户
   * @return 结果
   */
  SysUser save(SysUser sysUser);

  /**
   * 根据部门 ids 查询用户.
   *
   * @param ids ids
   * @return 结果
   */
  List<SysUser> searchByDepartments(List<Long> ids);
}
