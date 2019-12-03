package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.ChangeHistory;
import in.gaks.oneyard.model.constant.OneYard;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年12月3日 下午5:06:14
 */
@RepositoryRestResource(path = OneYard.CHANGE_HISTORY)
public interface ChangeHistoryRepository extends BaseRepository<ChangeHistory, Long> {

}