package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import javax.persistence.*;
import lombok.experimental.Accessors;
import lombok.*;
import org.hibernate.annotations.Where;


/**
 * plan_material.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "plan_material")
@Entity(name = "plan_material")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class PlanMaterial extends BaseEntity {

  /**
   * 物料分类编码.
   */
  private java.lang.Long planId;

  /**
   * 物料分类编码.
   */
  private java.lang.String materialTypeCode;

  /**
   * 物料分类名称.
   */
  private java.lang.String materialTypeName;

  /**
   * 物料编码.
   */
  private java.lang.String materialCode;

  /**
   * 物料名称.
   */
  private java.lang.String materialName;

  /**
   * 规格.
   */
  private java.lang.String specifications;

  /**
   * 型号.
   */
  private java.lang.String size;

  /**
   * 计量单位.
   */
  private java.lang.String unit;

  /**
   * 需求数量.
   */
  private java.lang.Integer number;

  /**
   * 需求日期.
   */
  private java.time.LocalDateTime date;

  /**
   * 货源是否确定.
   */
  private java.lang.Boolean isSourceGoods;

  /**
   * 期望供应商.
   */
  private java.lang.String expectationSupplier;

  /**
   * 固定供应商.
   */
  private java.lang.String fixedSupplier;

  /**
   * 需求库存组织.
   */
  private java.lang.String inventory;

  /**
   * 物料追踪码.
   */
  private java.lang.String materialTrackingCode;

}