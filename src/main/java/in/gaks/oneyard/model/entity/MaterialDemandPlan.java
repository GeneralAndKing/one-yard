package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.PlanStatus;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;


/**
 * material_demand_plan.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "material_demand_plan")
@Entity(name = "material_demand_plan")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class MaterialDemandPlan extends BaseEntity {
  /**
   * 需求计划类型.
   */
  private java.lang.String planType;

  /**
   * 需求部门.
   */
  private java.lang.Integer departmentId;

  /**
   * 汇总表id.
   */
  private java.lang.Long summaryId;

  /**
   * 需求人员.
   */
  private java.lang.String needPeople;

  /**
   * 需求月份（格式201911）.
   */
  private java.lang.String month;

  /**
   * 计划状态： 0 自由 1 提交审批 2 已提交汇总 3 已删除 4 已进入下一阶段.
   */
  @Enumerated(EnumType.ORDINAL)
  private PlanStatus planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批退回 3 审批通过 4已失效.
   */
  @Enumerated(EnumType.ORDINAL)
  private ApprovalStatus approvalStatus;

  /**
   * 是否预算内计划 0 false 1 true.
   */
  private java.lang.Boolean isBudgetPlan;

  /**
   * 修改意见.
   */
  private java.lang.String modifyIdea;

  /**
   * 物资清单.
   */
  @Transient
  private List<PlanMaterial> materials;
}
