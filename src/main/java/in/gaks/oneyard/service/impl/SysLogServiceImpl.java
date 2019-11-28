package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.SysLog;
import in.gaks.oneyard.repository.SysLogRepository;
import in.gaks.oneyard.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/28 下午5:06
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends BaseServiceImpl<SysLogRepository, SysLog, Long> implements
    SysLogService {

  private final SysLogRepository sysLogRepository;


  @Async
  @Override
  public void asyncSave(SysLog log) {
    sysLogRepository.save(log);
  }

}
