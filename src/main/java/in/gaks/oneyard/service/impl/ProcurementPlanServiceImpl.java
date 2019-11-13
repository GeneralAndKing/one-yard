package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.MaterialPlanSummary;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialPlanSummaryRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementPlanRepository;
import in.gaks.oneyard.service.MaterialPlanSummaryService;
import in.gaks.oneyard.service.PlanMaterialService;
import in.gaks.oneyard.service.ProcurementPlanService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 汇总表事务处理.
 *
 * @author Japoul
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class ProcurementPlanServiceImpl extends BaseServiceImpl<ProcurementPlanRepository,
    ProcurementPlan, Long>
    implements ProcurementPlanService {

  private final @NonNull ProcurementPlanRepository procurementPlanRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;
  private final @NonNull MaterialDemandPlanRepository materialPlanRepository;

  /**
   * 根据id获取完整的需求计划表.
   *
   * @param id 汇总表id
   * @return 完整的汇总表
   */
  @Override
  public ProcurementPlan findByIdToMaterials(Long id) {
    ProcurementPlan plan = procurementPlanRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("需求汇总表查询失败"));
    List<PlanMaterial> materials = planMaterialRepository
        .findAllByProcurementPlanIdAndStatus(id, MaterialStatus.INIT);
    plan.setPlanMaterials(materials);
    return plan;
  }

}
