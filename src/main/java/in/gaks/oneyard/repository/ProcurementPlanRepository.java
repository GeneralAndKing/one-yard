package in.gaks.oneyard.repository;

import in.gaks.oneyard.base.BaseRepository;
import in.gaks.oneyard.model.constant.OneYard;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月13日 下午10:34:45
 */
@RepositoryRestResource(path = OneYard.PROCUREMENT_PLAN)
public interface ProcurementPlanRepository extends BaseRepository<ProcurementPlan, Long> {

}