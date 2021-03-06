package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.Notification;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月10日 下午10:34:13
 */
@RepositoryRestResource(path = OneYard.NOTIFICATION)
public interface NotificationRepository extends BaseRepository<Notification, Long> {

  /**
   * 通过回复查询.
   *
   * @param receiverId 回复
   * @return 结果
   */
  @RestResource(path = "byReceiverId")
  List<Notification> findByReceiverId(@Param("receiverId") Long receiverId);

}