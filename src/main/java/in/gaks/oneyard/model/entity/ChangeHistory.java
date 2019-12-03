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
 * change_history.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年12月3日 下午5:06:14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "change_history")
@Entity(name = "change_history")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class ChangeHistory extends BaseEntity {

  /**
   * 订单id.
   */
  private java.lang.Integer orderId;

  /**
   * 物料id.
   */
  private java.lang.Integer procurementMaterialId;

  /**
   * 原采购数量.
   */
  private java.lang.Integer oldNumber;

  /**
   * 新采购数量.
   */
  private java.lang.Integer newNumber;

  /**
   * 原单价.
   */
  private Double oldPrice;

  /**
   * 新单价.
   */
  private Double newPrice;

  /**
   * 计价单位.
   */
  private java.lang.String chargeUnit;

  /**
   * 原计价数量.
   */
  private java.lang.Integer oldChargeNumber;

  /**
   * 新计价数量.
   */
  private java.lang.Integer newChargeNumber;

  /**
   * 变更状态 待审批 审批通过.
   */
  private java.lang.String status;

}
