package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.${EntityName};
import in.gaks.oneyard.model.constant.OneYard;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
* Repository.
* @author BugRui EchoCow Japoul
* @date ${.now?datetime}
*/
@RepositoryRestResource(path = OneYard.${path})
public interface ${EntityName}Repository extends BaseRepository<${EntityName}, Long> {

}