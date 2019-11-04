package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.entity.Inventory;
import in.gaks.oneyard.model.helper.OneYard;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午5:22:07
 */
@RepositoryRestResource(path = OneYard.INVENTORY)
public interface InventoryRepository extends BaseRepository<Inventory, Long> {

}