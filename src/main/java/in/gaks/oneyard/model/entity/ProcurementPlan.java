package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import java.util.List;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * procurement_plan.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月13日 下午10:34:45
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "procurement_plan")
@Entity(name = "procurement_plan")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class ProcurementPlan extends BaseEntity {
  /**
   * 年度采购计划、月度采购计划.
   */
  private java.lang.String planType;

  /**
   * 计划状态： 0 自由 1 已提交 2 已删除 3 已终止.
   */
  private java.lang.Integer planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批退回 3 审批通过 4已失效.
   */
  private java.lang.Integer approvalStatus;

  /**
   * 物资清单.
   */
  @Transient
  private List<PlanMaterial> planMaterials;
}
