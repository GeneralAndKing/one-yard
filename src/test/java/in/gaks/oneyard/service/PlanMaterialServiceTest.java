package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialRepository;
import in.gaks.oneyard.repository.MaterialTypeRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementPlanRepository;
import in.gaks.oneyard.repository.SysDepartmentRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.impl.PlanMaterialServiceImpl;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/4 下午9:53
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class PlanMaterialServiceTest {

  @Autowired
  private MaterialRepository materialRepository;
  @Autowired
  private MaterialTypeRepository materialTypeRepository;
  @Autowired
  private PlanMaterialRepository planMaterialRepository;
  @Autowired
  private ProcurementPlanRepository procurementPlanRepository;
  @Autowired
  private MaterialDemandPlanRepository materialPlanRepository;
  @Autowired
  private SysDepartmentRepository sysDepartmentRepository;
  @Autowired
  private ApprovalRepository approvalRepository;
  @Autowired
  private SysUserRepository sysUserRepository;
  @Autowired
  private NotificationRepository notificationRepository;
  @MockBean
  private NotifyUtil notifyUtil;
  private PlanMaterialService planMaterialService;

  @BeforeEach
  void setUp() {
    planMaterialService = new PlanMaterialServiceImpl(
        materialRepository, materialTypeRepository,
        planMaterialRepository, procurementPlanRepository,
        materialPlanRepository, sysDepartmentRepository,
        approvalRepository, sysUserRepository,
        notificationRepository, notifyUtil);
  }

  @Test
  @Tag("正常")
  @DisplayName("根据需求计划id获取完整的需求物资")
  void findAllByPlanId() {
    assertNotNull(planMaterialService.findAllByPlanId(2L, true));
    assertNotNull(planMaterialService.findAllByPlanId(2L, false));
  }

  @Test
  @Tag("异常")
  @DisplayName("根据需求计划id获取完整的需求物资")
  void findAllByPlanIdResourceNotFoundException() {
    assertThrows(ResourceNotFoundException.class,
        () -> planMaterialService.findAllByPlanId(1L, true));
    assertThrows(ResourceNotFoundException.class,
        () -> planMaterialService.findAllByPlanId(3L, true));
  }

  @Test
  @Tag("正常")
  @DisplayName("根据采购计划id获取完整的需求物资")
  void findAllByProcurementPlanId() {
    planMaterialService.findAllByProcurementPlanId(1L);
  }

  @Test
  @Tag("正常")
  @DisplayName("退回需求")
  void backPlanOrMaterial() {
    PlanMaterial one = planMaterialRepository.getOne(2L);
    planMaterialService.backPlanOrMaterial(true, one, new Approval());
    planMaterialService.backPlanOrMaterial(false, one, new Approval());
  }

  @Test
  @Tag("异常")
  @DisplayName("退回需求-资源未找到异常")
  void backPlanOrMaterialResourceNotFoundException() {
    assertThrows(ResourceNotFoundException.class,
        () -> planMaterialService
            .backPlanOrMaterial(false, planMaterialRepository.getOne(1L), new Approval()));
    assertThrows(ResourceNotFoundException.class,
        () -> planMaterialService
            .backPlanOrMaterial(false, planMaterialRepository.getOne(3L), new Approval()));
  }

  @Test
  @Tag("正常")
  @DisplayName("合并物料")
  void mergeMaterialPlan() {
    assertNotNull(
        planMaterialService.mergeMaterialPlan(planMaterialRepository.getOne(1L), List.of(2L)));
  }

  @Test
  @Tag("正常")
  @DisplayName("拆分物料")
  void splitMaterialPlan() {
    ArrayList<PlanMaterial> materials = new ArrayList<>();
    materials.add(new PlanMaterial());
    materials.add(new PlanMaterial());
    assertNotNull(
        planMaterialService.splitMaterialPlan(planMaterialRepository.getOne(1L), materials));
  }


  @Test
  @Tag("异常")
  @DisplayName("拆分物料-资源错误异常")
  void splitMaterialPlanResourceErrorException() {
    assertThrows(ResourceErrorException.class,
        () -> planMaterialService.splitMaterialPlan(new PlanMaterial(), List.of()));
    assertThrows(ResourceErrorException.class,
        () -> planMaterialService.splitMaterialPlan(planMaterialRepository.getOne(4L), List.of()));
  }

  @Test
  @Tag("异常")
  @DisplayName("拆分物料-资源未找到异常")
  void splitMaterialPlanResourceNotFoundException() {
    PlanMaterial planMaterial = new PlanMaterial();
    planMaterial.setId(500L);
    assertThrows(ResourceNotFoundException.class,
        () -> planMaterialService.splitMaterialPlan(planMaterial, List.of()));
  }
}