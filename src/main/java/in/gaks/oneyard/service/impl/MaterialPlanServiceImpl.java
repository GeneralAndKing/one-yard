package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.service.MaterialPlanService;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * .
 *
 * @author Japoul
 * @date 2019/11/2 下午11:01
 */
@Service
@RequiredArgsConstructor
public class MaterialPlanServiceImpl extends BaseServiceImpl<MaterialDemandPlanRepository,
    MaterialDemandPlan, Long>
    implements MaterialPlanService {

  private final @NonNull MaterialDemandPlanRepository materialPlanRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;

  /**
   * 保存/修改物料需求计划表.
   *
   * @param materialPlan 物料需求计划基础信息
   * @param materials 需求的物资列表
   * @return 是否保存成功
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void savePlanAndPlanMaterials(MaterialDemandPlan materialPlan,
      List<PlanMaterial> materials) {
    //判断计划基础信息不为空
    materialPlanRepository.save(materialPlan);
    //判断是否保存成功并返回了id
    if (Objects.isNull(materialPlan.getId())) {
      throw new ResourceErrorException("需求计划保存失败");
    }
    Long planId = materialPlan.getId();
    //循环保存
    materials.forEach(material -> {
      material.setPlanId(planId);
      planMaterialRepository.save(material);
    });
  }

  /**
   * 根据id获取完整的需求计划表.
   *
   * @param id 计划表id
   * @return 完整的计划表
   */
  @Override
  public MaterialDemandPlan findByIdToMaterial(Long id) {
    MaterialDemandPlan plan = materialPlanRepository.findById(id).orElseThrow(
        () -> new ResourceErrorException("需求计划查询失败"));
    plan.setMaterials(planMaterialRepository.findAllByPlanId(id));
    return plan;
  }

}
