package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
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
 * procurement_order.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月30日 下午8:05:18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "procurement_order")
@Entity(name = "procurement_order")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class ProcurementOrder extends BaseEntity {

  /**
   * 订单类型.
   */
  private java.lang.String type;

  /**
   * 单据编号.
   */
  private java.lang.String code;

  /**
   * .
   */
  private java.lang.String supplier;

  /**
   * .
   */
  private java.lang.String procurementDepartment;

  /**
   * .
   */
  private java.lang.String procurementDate;

  /**
   * 计划状态： 0 自由 1 提交审批  2 提交至汇总 3 已删除 4 已进入下一阶段.
   */
  private java.lang.String planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批退回 3 审批通过 4已失效.
   */
  private java.lang.String approvalStatus;

}
