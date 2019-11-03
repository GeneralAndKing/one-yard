package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysRole;
import in.gaks.oneyard.model.helper.OneYard;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:26
 */
@RepositoryRestResource(path = OneYard.SYS_ROLE)
public interface SysRoleRepository extends BaseRepository<SysRole, Long> {

}