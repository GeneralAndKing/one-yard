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
}
