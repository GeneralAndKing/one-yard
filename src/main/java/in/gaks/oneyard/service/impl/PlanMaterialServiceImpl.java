package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.entity.Material;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.repository.MaterialRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.service.PlanMaterialService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * .
 *
 * @author Japoul
 * @date 2019/11/11 下午11:01
 */
@Service
@RequiredArgsConstructor
public class PlanMaterialServiceImpl extends BaseServiceImpl<PlanMaterialRepository,
    PlanMaterial, Long>
    implements PlanMaterialService {

  private final @NonNull MaterialRepository materialRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;

  /**
   * 根据计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   */
  @Override
  public List<PlanMaterial> findAllByPlanId(Long id) {
    return planMaterialRepository.findAllByPlanId(id)
        .stream().map(planMaterial -> {
          Material material = materialRepository.findById(planMaterial.getMaterialId()).orElseThrow(
              () -> new ResourceErrorException("物料主数据查询失败"));
          planMaterial.setMaterial(material);
          return planMaterial;
        }).collect(Collectors.toList());
  }

}
