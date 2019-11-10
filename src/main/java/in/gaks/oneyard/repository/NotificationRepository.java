package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.Notification;
import in.gaks.oneyard.model.constant.OneYard;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月10日 下午10:34:13
 */
@RepositoryRestResource(path = OneYard.NOTIFICATION)
public interface NotificationRepository extends BaseRepository<Notification, Long> {

}