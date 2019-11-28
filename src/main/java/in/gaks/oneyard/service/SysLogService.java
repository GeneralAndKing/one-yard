package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.SysLog;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/28 下午5:01
 */
public interface SysLogService extends BaseService<SysLog, Long> {

  /**
   * 异步存储.
   *
   * @param log 日志
   */
  void asyncSave(SysLog log);
}
