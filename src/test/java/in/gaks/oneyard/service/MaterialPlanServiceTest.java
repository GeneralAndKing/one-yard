package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.impl.MaterialPlanServiceImpl;
import in.gaks.oneyard.util.NotifyUtil;
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
 * @date 2019/12/4 下午9:14
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class MaterialPlanServiceTest {

  @Autowired
  private MaterialDemandPlanRepository materialPlanRepository;
  @Autowired
  private PlanMaterialRepository planMaterialRepository;
  @Autowired
  private ApprovalRepository approvalRepository;
  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private SysUserRepository sysUserRepository;
  @MockBean
  private MaterialPlanSummaryService materialPlanSummaryService;
  @MockBean
  private PlanMaterialService planMaterialService;
  @MockBean
  private ProcurementPlanService procurementPlanService;
  @MockBean
  private NotifyUtil notifyUtil;
  private MaterialPlanService materialPlanService;

  @BeforeEach
  void setUp() {
    materialPlanService = new MaterialPlanServiceImpl(
        materialPlanRepository, planMaterialRepository, approvalRepository,
        notificationRepository, sysUserRepository, materialPlanSummaryService,
        planMaterialService, procurementPlanService, notifyUtil);
  }

  @Test
  @Tag("正常")
  @DisplayName("保存物料需求计划表")
  void savePlanAndPlanMaterials() {
    MaterialDemandPlan plan = new MaterialDemandPlan();
    PlanMaterial material = new PlanMaterial();
    material.setId(1L);
    materialPlanService.savePlanAndPlanMaterials(plan, List.of(material));
  }

  @Test
  @Tag("正常")
  @DisplayName("根据id获取完整的需求计划表")
  void findByIdToMaterial() {
    assertNotNull(materialPlanService.findByIdToMaterial(1L));
  }

  @Test
  @Tag("异常")
  @DisplayName("根据id获取完整的需求计划表-资源未找到异常")
  void findByIdToMaterialResourceNotFoundException() {
    assertThrows(ResourceNotFoundException.class,
        () -> materialPlanService.findByIdToMaterial(10000L));
  }

  @Test
  @Tag("正常")
  @DisplayName("主管审批需求物料计划")
  void approvalMaterialPlan() {
    MaterialDemandPlan plan = materialPlanRepository.getOne(1L);
    Approval approval = new Approval();
    materialPlanService.approvalMaterialPlan(plan, approval.setResult("部门主管审批通过"));
    materialPlanService.approvalMaterialPlan(plan, approval.setResult("需求计划审批退回通知"));
  }

  @Test
  @Tag("正常")
  @DisplayName("撤回审批")
  void withdrawApproval() {
    materialPlanService.withdrawApproval(4L);
  }

  @Test
  @Tag("异常")
  @DisplayName("撤回审批-资源错误异常")
  void withdrawApprovalResourceErrorException() {
    assertThrows(ResourceErrorException.class, () -> materialPlanService.withdrawApproval(1L));
  }
}