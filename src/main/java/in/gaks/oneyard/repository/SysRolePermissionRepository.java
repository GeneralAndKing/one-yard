package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysRolePermission;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(exported = false)
public interface SysRolePermissionRepository extends
    BaseRepository<SysRolePermission, Long> {

}