package in.gaks.oneyard.service;

import static org.junit.jupiter.api.Assertions.*;

import in.gaks.oneyard.model.entity.MaterialDemandPlan;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.repository.MaterialDemandPlanRepository;
import in.gaks.oneyard.repository.MaterialPlanSummaryRepository;
import in.gaks.oneyard.service.impl.MaterialPlanSummaryServiceImpl;
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
 * @date 2019/12/4 下午8:33
 */
@DataJpaTest
@ExtendWith(SpringExtension.class)
class MaterialPlanSummaryServiceTest {

  @Autowired
  private MaterialPlanSummaryRepository materialPlanSummaryRepository;
  @Autowired
  private MaterialDemandPlanRepository materialDemandPlanRepository;
  @MockBean
  private PlanMaterialService planMaterialService;
  private MaterialPlanSummaryService materialPlanSummaryService;

  @BeforeEach
  void setUp() {
    materialPlanSummaryService = new MaterialPlanSummaryServiceImpl
        (materialPlanSummaryRepository, planMaterialService);
  }

  @Test
  @Tag("正常")
  @DisplayName("根据id获取完整的需求计划表")
  void findByIdToMaterialSummary() {
    assertNotNull(materialPlanSummaryService.findByIdToMaterialSummary(1L));
  }

  @Test
  @Tag("异常")
  @DisplayName("根据id获取完整的需求计划表-资源未找到异常")
  void findByIdToMaterialSummaryResourceNotFoundException() {
    assertThrows(ResourceNotFoundException.class,
        () -> materialPlanSummaryService.findByIdToMaterialSummary(1000L));
  }

  @Test
  @Tag("正常")
  @DisplayName("自动汇总-紧急计划")
  void summaryMaterialPlanOne() {
    MaterialDemandPlan plan = materialDemandPlanRepository.getOne(1L);
    assertNull(materialPlanSummaryService.summaryMaterialPlan(plan));
  }

  @Test
  @Tag("正常")
  @DisplayName("自动汇总-年度计划")
  void summaryMaterialPlaTwo() {
    MaterialDemandPlan plan = materialDemandPlanRepository.getOne(2L);
    assertNotNull(materialPlanSummaryService.summaryMaterialPlan(plan));
  }

  @Test
  @Tag("正常")
  @DisplayName("自动汇总-月度计划")
  void summaryMaterialPlaThree() {
    MaterialDemandPlan plan = materialDemandPlanRepository.getOne(3L);
    assertNotNull(materialPlanSummaryService.summaryMaterialPlan(plan));
  }
}