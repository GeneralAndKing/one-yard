package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.PlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementPlan;
import in.gaks.oneyard.model.entity.dto.ProcurementPlanDto;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.MaterialRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementPlanRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.repository.dto.ProcurementPlanDtoRepository;
import in.gaks.oneyard.service.impl.ProcurementPlanServiceImpl;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/4 下午11:09
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProcurementPlanServiceTest {

  @Autowired
  private ProcurementPlanRepository procurementPlanRepository;
  @Mock
  private ProcurementPlanDtoRepository procurementPlanDtoRepository;
  @Autowired
  private PlanMaterialRepository planMaterialRepository;
  @Autowired
  private ApprovalRepository approvalRepository;
  @Autowired
  private SysUserRepository sysUserRepository;
  @Autowired
  private MaterialRepository materialRepository;
  @Autowired
  private NotificationRepository notificationRepository;
  @MockBean
  private NotifyUtil notifyUtil;
  private ProcurementPlanService procurementPlanService;

  @BeforeEach
  void setUp() {
    procurementPlanService = new ProcurementPlanServiceImpl(
        procurementPlanRepository, procurementPlanDtoRepository, planMaterialRepository,
        materialRepository, approvalRepository, sysUserRepository, notificationRepository,
        notifyUtil
    );
    ArrayList<ProcurementPlanDto> procurementPlanDtoList = new ArrayList<>();
    ProcurementPlanDto dto1 = new ProcurementPlanDto();
    dto1.setMaterialId(1L);
    dto1.setMaterialNumber(1L);
    dto1.setMaterialLowNumber(1L);
    procurementPlanDtoList.add(dto1);
    when(procurementPlanDtoRepository.searchInfoById(1L)).thenReturn(null);
    when(procurementPlanDtoRepository.searchInfoById(2L)).thenReturn(procurementPlanDtoList);
  }

  @Test
  @Tag("正确")
  @DisplayName("根据id查询完整的采购计划表")
  void findByIdToMaterials() {
    assertNotNull(procurementPlanService.findByIdToMaterials(2L));
  }

  @Test
  @Tag("异常")
  @DisplayName("根据id查询完整的采购计划表-资源未找到异常")
  void findByIdToMaterialsResourceNotFoundException() {
    assertThrows(ResourceNotFoundException.class,
        () -> procurementPlanService.findByIdToMaterials(1L));
  }

  @Test
  @Tag("正确")
  @DisplayName("采购主管/财务审批采购计划")
  void approvalProcurementPlan() {
    ProcurementPlan procurementPlan = new ProcurementPlan();
    procurementPlan.setCreateUser("admin");
    procurementPlanService
        .approvalProcurementPlan(procurementPlan, new Approval().setResult("采购主管审批通过"));
    procurementPlanService
        .approvalProcurementPlan(procurementPlan, new Approval().setResult("采购主管审批退回"));
    procurementPlanService
        .approvalProcurementPlan(procurementPlan, new Approval().setResult("财务审批通过"));
    procurementPlanService
        .approvalProcurementPlan(procurementPlan, new Approval().setResult("财务审批退回"));
  }

  @Test
  @Tag("正确")
  @DisplayName("撤回审批")
  void withdrawApproval() {
    ProcurementPlan procurementPlan = new ProcurementPlan()
        .setPlanStatus(PlanStatus.APPROVAL)
        .setApprovalStatus(ApprovalStatus.APPROVAL_ING);
    procurementPlanService.withdrawApproval(procurementPlan, "PLANER");
    procurementPlan.setPlanStatus(PlanStatus.PROCUREMENT_OK)
        .setApprovalStatus(ApprovalStatus.APPROVAL_ING);
    procurementPlanService.withdrawApproval(procurementPlan, "SUPERVISOR");
  }

  @Test
  @Tag("异常")
  @DisplayName("撤回审批-资源错误异常")
  void withdrawApprovalResourceErrorException() {
    assertThrows(ResourceErrorException.class, () -> procurementPlanService
        .withdrawApproval(null, "null"));
  }

  @Test
  @Tag("正确")
  @DisplayName("保存采购计划表")
  void savePlanAndPlanMaterials() {
    PlanMaterial one = planMaterialRepository.getOne(1L);
    one.setSupplyMode("库存供应");
    procurementPlanService
        .savePlanAndPlanMaterials(new ProcurementPlan(),
            List.of(one,
                new PlanMaterial().setSupplyMode("采购").setPurchaseDate("2019-0121"),
                new PlanMaterial().setSupplyMode("库存供应")
            )
        );
    ProcurementPlan plan = procurementPlanRepository.getOne(1L);
    procurementPlanService.savePlanAndPlanMaterials(plan, List.of(new PlanMaterial()));
  }

  @Test
  @Tag("异常")
  @DisplayName("保存采购计划表-资源错误异常")
  void savePlanAndPlanMaterialsResourceErrorException() {
    assertThrows(ResourceErrorException.class, () -> procurementPlanService
        .savePlanAndPlanMaterials(new ProcurementPlan(), List.of(new PlanMaterial())));
    assertThrows(ResourceErrorException.class, () -> procurementPlanService
        .savePlanAndPlanMaterials(new ProcurementPlan(), List.of(planMaterialRepository.getOne(6L))));
  }


  @Test
  @Tag("正确")
  @DisplayName("获取紧急采购计划id")
  void getUrgentProcurementId() {
    procurementPlanService.getUrgentProcurementId("紧急类型需求计划");
  }
}