package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * material_demand_plan.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 *
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
  private java.lang.Integer summaryId;

  /**
   * 需求人员.
   */
  private java.lang.String needPeople;

  /**
   * 计划状态： 0 自由 1 已提交汇总 2 已删除 3 已终止.
   */
  private java.lang.Integer planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批退回 3 审批通过.
   */
  private java.lang.Integer approvalStatus;

  /**
   * 是否预算内计划 0 false 1 true.
   */
  private java.lang.Boolean isBudgetPlan;

}
