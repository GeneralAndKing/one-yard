package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * material_plan_summary.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "material_plan_summary")
@Entity(name = "material_plan_summary")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class MaterialPlanSummary extends BaseEntity {
  /**
   * 计划状态： 0 自由 1 已提交汇总 2 已删除 3 已终止.
   */
  private java.lang.Integer planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批退回 3 审批通过.
   */
  private java.lang.Integer approvalStatus;

}
