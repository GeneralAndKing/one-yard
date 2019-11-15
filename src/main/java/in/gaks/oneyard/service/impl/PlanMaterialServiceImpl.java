package in.gaks.oneyard.service.impl;

import in.gaks.oneyard.base.impl.BaseServiceImpl;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.entity.Material;
import in.gaks.oneyard.model.entity.MaterialType;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialRepository;
import in.gaks.oneyard.repository.MaterialTypeRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.SysDepartmentRepository;
import in.gaks.oneyard.service.MaterialPlanService;
import in.gaks.oneyard.service.PlanMaterialService;
import java.util.List;
import java.util.stream.Collectors;
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
  private final @NonNull MaterialTypeRepository materialTypeRepository;
  private final @NonNull PlanMaterialRepository planMaterialRepository;
  private final @NonNull MaterialDemandPlanRepository materialPlanRepository;
  private final @NonNull SysDepartmentRepository sysDepartmentRepository;

  /**
   * 根据需求计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   */
  @Override
  public List<PlanMaterial> findAllByPlanId(Long id) {
    return planMaterialRepository.findAllByPlanId(id)
        .stream().peek(planMaterial -> {
          planMaterial.setDepartmentName(getDepartmentNameByPlanId(planMaterial.getPlanId()));
          Material material = materialRepository.findById(planMaterial.getMaterialId()).orElseThrow(
              () -> new ResourceNotFoundException("物料主数据查询失败"));
          planMaterial.setMaterial(material);
          MaterialType materialType = materialTypeRepository
              .findById(planMaterial.getMaterialTypeId()).orElseThrow(
                  () -> new ResourceNotFoundException("物料类别主数据查询失败"));
          planMaterial.setMaterialType(materialType);
        }).collect(Collectors.toList());
  }

  /**
   * 根据采购计划id获取完整的需求物资.
   *
   * @param id 计划表id
   * @return 完整的需求物资数据
   */
  @Override
  public List<PlanMaterial> findAllByProcurementPlanId(Long id) {
    return planMaterialRepository.findAllByProcurementPlanIdAndStatus(id, MaterialStatus.INIT)
        .stream().peek(planMaterial -> {
          Material material = materialRepository.findById(planMaterial.getMaterialId()).orElseThrow(
              () -> new ResourceNotFoundException("物料主数据查询失败"));
          planMaterial.setMaterial(material);
          MaterialType materialType = materialTypeRepository
              .findById(planMaterial.getMaterialTypeId()).orElseThrow(
                  () -> new ResourceNotFoundException("物料类别主数据查询失败"));
          planMaterial.setMaterialType(materialType);
          planMaterial.setDepartmentName(getDepartmentNameByPlanId(planMaterial.getPlanId()));
        }).collect(Collectors.toList());
  }

  /**
   * 根据需求计划id查询所需部门.
   *
   * @param planId 需求计划id
   */
  @Override
  public String getDepartmentNameByPlanId(Long planId) {
    Long departmentId = materialPlanRepository.findById(planId)
        .orElseThrow(() -> new ResourceNotFoundException("需求计划查询失败")).getDepartmentId();
    return sysDepartmentRepository.findById(departmentId)
        .orElseThrow(() -> new ResourceNotFoundException("需求部门查询失败")).getName();
  }
}
