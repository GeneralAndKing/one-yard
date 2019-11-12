package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.ApprovalTypeStatus;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;


/**
 * approval.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "approval")
@Entity(name = "approval")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class Approval extends BaseEntity {
  /**
   * 审批类型 0 物料需求计划审批 1 采购计划审批.
   */
  @Enumerated(EnumType.ORDINAL)
  private ApprovalTypeStatus approvalType;

  /**
   * 需求计划汇总表id.
   */
  private java.lang.Long planId;

  /**
   * 审批意见（结果） （审批退回 审批通过）.
   */
  private java.lang.String result;

  /**
   * 说明（对审批结果的解释）.
   */
  private java.lang.String description;

}
