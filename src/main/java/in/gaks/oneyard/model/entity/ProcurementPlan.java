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
 * procurement_plan.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月10日 下午10:17:20
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
   * 采购计划表物资清单.
   */
  @Transient
  private List<PlanMaterial> planMaterials;

}
