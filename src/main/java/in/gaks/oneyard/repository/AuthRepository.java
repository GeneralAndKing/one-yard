package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.Auth;
import in.gaks.oneyard.model.helper.OneYard;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.AUTH)
public interface AuthRepository extends BaseRepository<Auth, Long> {

}