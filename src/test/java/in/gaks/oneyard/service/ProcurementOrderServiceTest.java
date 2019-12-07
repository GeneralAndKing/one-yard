package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Sets;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.ProcurementApprovalStatus;
import in.gaks.oneyard.model.constant.ProcurementMaterialStatus;
import in.gaks.oneyard.model.constant.ProcurementOrderPlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.ChangeHistory;
import in.gaks.oneyard.model.entity.OrderTerms;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.ChangeHistoryRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.OrderTermsRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementMaterialRepository;
import in.gaks.oneyard.repository.ProcurementOrderRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.impl.ProcurementOrderServiceImpl;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
 * @date 2019/12/3 下午10:08
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProcurementOrderServiceTest {

  @Autowired
  private ProcurementOrderRepository procurementOrderRepository;
  @Autowired
  private ProcurementMaterialRepository procurementMaterialRepository;
  @Autowired
  private PlanMaterialRepository planMaterialRepository;
  @Autowired
  private OrderTermsRepository orderTermsRepository;
  @Autowired
  private ApprovalRepository approvalRepository;
  @Autowired
  private SysUserRepository sysUserRepository;
  @Autowired
  private NotificationRepository notificationRepository;
  @Autowired
  private ChangeHistoryRepository changeHistoryRepository;
  @MockBean
  private NotifyUtil notifyUtil;
  private ProcurementOrderService procurementOrderService;

  @BeforeEach
  void setUp() {
    procurementOrderService = new ProcurementOrderServiceImpl(
        procurementOrderRepository,
        procurementMaterialRepository,
        planMaterialRepository, orderTermsRepository,
        approvalRepository, sysUserRepository,
        notificationRepository, changeHistoryRepository,
        notifyUtil);
  }

  @Test
  @Tag("正常")
  @DisplayName("采购部门主管审批采购订单")
  void approvalProcurementOrderTest() {
    // TODO: 测试用例
    procurementOrderService.approvalProcurementOrder(new ProcurementOrder(), new Approval());
  }

  @Test
  @DisplayName("撤回审批请求")
  @Tag("正常")
  void withdrawApprovalTest() {
    procurementOrderService.withdrawApproval(1L);
    Optional<ProcurementOrder> orderOptional = procurementOrderRepository.findById(1L);
    assertTrue(orderOptional.isPresent());
    ProcurementOrder order = orderOptional.get();
    assertEquals(ProcurementApprovalStatus.NO_SUBMIT, order.getApprovalStatus());
    assertEquals(ProcurementOrderPlanStatus.NO_SUBMIT, order.getPlanStatus());
  }

  @Test
  @Tag("异常")
  @DisplayName("撤回审批请求资源未找到异常 ╯°□°）╯")
  void withdrawApprovalTestResourceNotFoundException() {
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class, () -> procurementOrderService.withdrawApproval(1000L));
    assertEquals("找不到对应的采购订单", exception.getMessage());
  }

  @Test
  @Tag("异常")
  @DisplayName("撤回审批请求资源错误异常 ╯°□°）╯")
  void withdrawApprovalTestResourceErrorException() {
    ResourceErrorException exception = assertThrows(ResourceErrorException.class,
        () -> procurementOrderService.withdrawApproval(2L));
    assertEquals("该采购订单状态发生改变，不可撤回，请刷新后再试！", exception.getMessage());
  }

  @Test
  @Tag("正常")
  @DisplayName("保存采购订单表")
  void saveProcurementOrderTest() {
    PlanMaterial planMaterial = planMaterialRepository.getOne(1L);
    ProcurementOrder order = new ProcurementOrder();
    ProcurementMaterial material1 = new ProcurementMaterial();
    material1.setPlanMaterialId(planMaterial.getId());
    ProcurementMaterial material2 = new ProcurementMaterial();
    material2.setPlanMaterialId(planMaterial.getId());
    List<ProcurementMaterial> materials = List.of(material1, material2);
    List<OrderTerms> orderTerms = List.of(new OrderTerms(), new OrderTerms(), new OrderTerms());
    procurementOrderService.saveProcurementOrder(order, materials, orderTerms);
    assertNotNull(order.getId());
  }

  @Test
  @Tag("异常")
  @DisplayName("保存采购订单表资源未找到异常 ╯°□°）╯")
  void saveProcurementOrderTestResourceErrorException() {
    ProcurementOrder order = new ProcurementOrder();
    ProcurementMaterial material = new ProcurementMaterial();
    material.setPlanMaterialId(1000L);
    List<ProcurementMaterial> materials = List.of(material);
    List<OrderTerms> orderTerms = List.of(new OrderTerms(), new OrderTerms(), new OrderTerms());
    assertThrows(ResourceNotFoundException.class,
        () -> procurementOrderService.saveProcurementOrder(order, materials, orderTerms));
  }

  @Test
  @Tag("正常")
  @DisplayName("变更订单信息")
  void changeProcurementOrder() {
    List<ProcurementMaterial> materials = procurementMaterialRepository.findAllByOrderId(1L);
    ProcurementMaterial material0 = materials.get(0);
    material0.setChargeNumber(10);
    ProcurementMaterial material1 = materials.get(1);
    material1.setProcurementNumber(200);
    procurementOrderService.changeProcurementOrder(1L, List.of(material0, material1));
    ProcurementOrder one = procurementOrderRepository.getOne(1L);
    assertAll("状态检验",
        () -> assertEquals(ProcurementOrderPlanStatus.CHANGED, one.getPlanStatus()),
        () -> assertEquals(ProcurementApprovalStatus.APPROVAL_ING, one.getApprovalStatus()),
        () -> assertEquals(ProcurementMaterialStatus.CHANGED, material0.getStatus()),
        () -> assertEquals(ProcurementMaterialStatus.CHANGED, material1.getStatus())
    );
    List<ChangeHistory> histories = changeHistoryRepository.findAllByOrderId(1L);
    assertEquals(2, histories.size());
    ChangeHistory history1 = histories.get(0);
    ChangeHistory history2 = histories.get(1);
    assertAll("数据校验",
        () -> assertEquals(material0.getChargeNumber(), history1.getNewChargeNumber()),
        () -> assertEquals(material1.getProcurementNumber(), history2.getNewNumber())
    );
  }
}