package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;


/**
 * procurement_material.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月30日 下午8:05:18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "procurement_material")
@Entity(name = "procurement_material")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class ProcurementMaterial extends BaseEntity {

  /**
   * 订单id.
   */
  private java.lang.Long orderId;

  /**
   * 物料id.
   */
  private java.lang.Long materialId;

  /**
   * 采购单位.
   */
  private java.lang.String procurementUnit;

  /**
   * 采购数量.
   */
  private java.lang.Integer procurementNumber;

  /**
   * 供应商.
   */
  private java.lang.String supplier;

  /**
   * 计价单位.
   */
  private java.lang.String chargeUnit;

  /**
   * 计价数量.
   */
  private java.lang.Integer chargeNumber;

  /**
   * 交货日期.
   */
  private java.lang.String deliveryDate;

  /**
   * 单价.
   */
  private Double unitPrice;

  /**
   * 含税单价（包含税费的单价）.
   */
  private Double taxableUnitPrice;

  /**
   * 税率 x%.
   */
  private java.lang.String taxRate;

  /**
   * 税额（总共交多少税）.
   */
  private Double taxAmount;

  /**
   * 总价（不含税）.
   */
  private Double totalPrice;

  /**
   * 含税总价（税费+总价）.
   */
  private Double taxTotalPrice;

  /**
   * 是否是赠品.
   */
  private java.lang.Boolean isComplimentary;

  /**
   * 需求部门.
   */
  private java.lang.String demandDepartment;

  /**
   * 收料部门.
   */
  private java.lang.String materialReceivingDepartment;

  /**
   * 关联需求物料id.
   */
  private java.lang.Long planMaterialId;

  /**
   * 订单物料状态.
   */
  private java.lang.String status;

}
