package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.ProcurementOrderPlanStatus;
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
   * 框架协议编号.
   */
  private java.lang.String frameworkAgreementCode;

  /**
   * 供应商.
   */
  private java.lang.String supplier;

  /**
   * 采购部门.
   */
  private java.lang.String procurementDepartment;

  /**
   * 采购日期.
   */
  private java.lang.String procurementDate;

  /**
   * 交货日期.
   */
  private java.lang.String deliveryDate;

  /**
   * 计划状态： 待提交，提交审批，已关闭，未完成，已完成，已变更，已生效，已作废.
   */
  @Enumerated(EnumType.ORDINAL)
  private ProcurementOrderPlanStatus planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批退回 3 审批通过 4已失效.
   */
  @Enumerated(EnumType.ORDINAL)
  private ApprovalStatus approvalStatus;

}
