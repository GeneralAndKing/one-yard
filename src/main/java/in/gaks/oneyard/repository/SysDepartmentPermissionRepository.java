package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.SysDepartmentPermission;
import in.gaks.oneyard.model.helper.OneYard;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@RepositoryRestResource(path = OneYard.SYS_DEPARTMENT_PERMISSION)
public interface SysDepartmentPermissionRepository extends
    BaseRepository<SysDepartmentPermission, Long> {

}