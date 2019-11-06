package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.PlanStatus;
import java.util.List;
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
   * 计划状态： 0 自由 2 已删除 3 已终止.
   */
  @Enumerated(EnumType.ORDINAL)
  private PlanStatus planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批通过.
   */
  @Enumerated(EnumType.ORDINAL)
  private ApprovalStatus approvalStatus;

  /**
   * 汇总表物资清单.
   */
  @Transient
  private List<PlanMaterial> planMaterials;

}
