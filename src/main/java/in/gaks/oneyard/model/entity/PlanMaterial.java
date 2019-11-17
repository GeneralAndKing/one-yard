package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.MaterialStatus;
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
 * plan_material.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
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
   * 需求计划id.
   */
  private java.lang.Long planId;

  /**
   * 采购计划id.
   */
  private java.lang.Long procurementPlanId;

  /**
   * 物料分类id.
   */
  private java.lang.Long materialTypeId;

  /**
   * 物料id.
   */
  private java.lang.Long materialId;

  /**
   * 需求数量.
   */
  private java.lang.Integer number;

  /**
   * 需求日期.
   */
  private java.lang.String date;

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
   * 供应方式 （库存供应、采购）.
   */
  private java.lang.String supplyMode;

  /**
   * 供应数量.
   */
  private java.lang.Long supplyNumber;

  /**
   * 采购日期.
   */
  private java.lang.String purchaseDate;

  /**
   * 计划来源.
   */
  private java.lang.String planSource;

  /**
   * 需求物料状态 0 初始 1 退回.
   */
  @Enumerated(EnumType.ORDINAL)
  private MaterialStatus status;

  /**
   * 需求库存组织.
   */
  private java.lang.String inventory;

  /**
   * 物料追踪码.
   */
  private java.lang.String materialTrackingCode;

  /**
   * 物料类别数据.
   */
  @Transient
  private MaterialType materialType;

  /**
   * 物料数据.
   */
  @Transient
  private Material material;

  /**
   * 部门.
   */
  @Transient
  private String departmentName;

  /**
   * 在途数量.
   */
  @Transient
  private Long inTransitNum;

  /**
   * 可用库存.
   */
  @Transient
  private Long availableNum;

  /**
   * 已占库存.
   */
  @Transient
  private Long occupiedNum;
}
