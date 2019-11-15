package in.gaks.oneyard.service;

import in.gaks.oneyard.base.BaseService;
import in.gaks.oneyard.model.entity.PlanMaterial;
import java.util.List;

/**
 * Repository.
 *
 * @author Japoul
 * @date 2019年11月11日 下午10:17:20
 */
public interface PlanMaterialService extends BaseService<PlanMaterial, Long> {

  /**
   * 根据计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   */
  List<PlanMaterial> findAllByPlanId(Long id);

  /**
   * 根据采购计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   */
  List<PlanMaterial> findAllByProcurementPlanId(Long id);
}
