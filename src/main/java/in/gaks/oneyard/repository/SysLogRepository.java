package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.SysLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.SYS_LOG)
public interface SysLogRepository extends BaseRepository<SysLog, Long> {

  /**
   * 删除.
   *
   * @param createTime 创建时间
   * @return 操作条数
   */
  @Modifying
  @RestResource(path = "deleteByCreateTime")
  @Transactional(rollbackForClassName = "java.lang.Exception")
  @Query(value = "update sys_log set is_enable = false where create_time <= :createTime", nativeQuery = true)
  int deleteByCreateTime(@Param("createTime") String createTime);

}