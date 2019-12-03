package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.ProcurementOrderPlanStatus;
import in.gaks.oneyard.model.entity.Approval;
import in.gaks.oneyard.model.entity.OrderTerms;
import in.gaks.oneyard.model.entity.PlanMaterial;
import in.gaks.oneyard.model.entity.ProcurementMaterial;
import in.gaks.oneyard.model.entity.ProcurementOrder;
import in.gaks.oneyard.model.exception.ResourceErrorException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.ApprovalRepository;
import in.gaks.oneyard.repository.NotificationRepository;
import in.gaks.oneyard.repository.OrderTermsRepository;
import in.gaks.oneyard.repository.PlanMaterialRepository;
import in.gaks.oneyard.repository.ProcurementMaterialRepository;
import in.gaks.oneyard.repository.ProcurementOrderRepository;
import in.gaks.oneyard.repository.SysUserRepository;
import in.gaks.oneyard.service.impl.ProcurementOrderServiceImpl;
import in.gaks.oneyard.util.NotifyUtil;
import java.util.List;
import java.util.Optional;
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
        notificationRepository, notifyUtil);
    ProcurementOrder order1 = new ProcurementOrder()
        .setApprovalStatus(ApprovalStatus.APPROVAL_ING)
        .setPlanStatus(ProcurementOrderPlanStatus.APPROVAL);
    procurementOrderRepository.save(order1);
    ProcurementOrder order2 = new ProcurementOrder()
        .setApprovalStatus(ApprovalStatus.APPROVAL_OK)
        .setPlanStatus(ProcurementOrderPlanStatus.CLOSE);
    procurementOrderRepository.save(order2);

    ProcurementMaterial material = new ProcurementMaterial();
    material.setOrderId(order1.getId());
    procurementMaterialRepository.save(material);
  }

  @Test
  @Tag("正确")
  @DisplayName("采购部门主管审批采购订单")
  void approvalProcurementOrderTest() {
    // TODO: 测试用例
    ProcurementOrder order = new ProcurementOrder();
    Approval approval = new Approval();
    procurementOrderService.approvalProcurementOrder(order, approval);
  }

  @Test
  @DisplayName("撤回审批请求")
  @Tag("正确")
  void withdrawApprovalTest() {
    ProcurementOrder exist = procurementOrderRepository.findAll().get(0);
    procurementOrderService.withdrawApproval(exist.getId());
    Optional<ProcurementOrder> orderOptional = procurementOrderRepository.findById(exist.getId());
    assertTrue(orderOptional.isPresent());
    ProcurementOrder order = orderOptional.get();
    assertEquals(ApprovalStatus.NO_SUBMIT, order.getApprovalStatus());
    assertEquals(ProcurementOrderPlanStatus.NO_SUBMIT, order.getPlanStatus());
  }

  @Test
  @Tag("异常")
  @DisplayName("撤回审批请求资源未找到异常情况 ╯°□°）╯")
  void withdrawApprovalTestResourceNotFoundException() {
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class, () -> procurementOrderService.withdrawApproval(1000L));
    assertEquals("找不到对应的采购订单", exception.getMessage());
  }

  @Test
  @Tag("异常")
  @DisplayName("撤回审批请求资源错误异常情况 ╯°□°）╯")
  void withdrawApprovalTestResourceErrorException() {
    ProcurementOrder order = procurementOrderRepository.findAll().get(1);
    ResourceErrorException exception = assertThrows(ResourceErrorException.class,
        () -> procurementOrderService.withdrawApproval(order.getId()));
    assertEquals("该采购订单状态发生改变，不可撤回，请刷新后再试！", exception.getMessage());
  }

  @Test
  @Tag("正确")
  @DisplayName("保存采购订单表")
  void saveProcurementOrderTest() {
    PlanMaterial planMaterial = new PlanMaterial();
    planMaterialRepository.save(planMaterial);
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
  @DisplayName("保存采购订单表资源未找到异常")
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
  @Tag("正确")
  @DisplayName("删除采购订单的明细信息（物料）")
  void deleteProcurementMaterialTest() {

  }

  @Test
  @Tag("异常")
  @DisplayName("删除采购订单的明细信息（物料）资源错误异常")
  void deleteProcurementMaterialTestResourceErrorException() {
    ProcurementMaterial material = procurementMaterialRepository.findAll().get(0);
    ProcurementOrder order = procurementOrderRepository.findAll().get(0);
    order.setApprovalStatus(ApprovalStatus.APPROVAL_ING);
    material.setOrderId(order.getId());
    procurementMaterialRepository.save(material);
    assertThrows(ResourceErrorException.class,
        () -> procurementOrderService.deleteProcurementMaterial(material.getId()));
    order.setApprovalStatus(ApprovalStatus.APPROVAL_OK);
    procurementMaterialRepository.save(material);
    assertThrows(ResourceErrorException.class,
        () -> procurementOrderService.deleteProcurementMaterial(material.getId()));
    order.setApprovalStatus(null);
    order.setPlanStatus(ProcurementOrderPlanStatus.EFFECTIVE);
    procurementMaterialRepository.save(material);
    assertThrows(ResourceErrorException.class,
        () -> procurementOrderService.deleteProcurementMaterial(material.getId()));
  }
}