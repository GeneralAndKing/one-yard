package in.gaks.oneyard.model.entity.dto;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.ApprovalStatus;
import in.gaks.oneyard.model.constant.MaterialStatus;
import in.gaks.oneyard.model.constant.PlanStatus;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Immutable;

/**
 * 视图查询.
 *
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/16 下午2:27
 */
@Data
@Entity
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProcurementPlanDto extends BaseEntity {

  /**
   * 年度采购计划、月度采购计划.
   */
  private java.lang.String planType;

  /**
   * 计划状态： 0 自由 1 已提交 2 已删除 3 已终止.
   */
  @Enumerated(EnumType.ORDINAL)
  private PlanStatus planStatus;

  /**
   * 审批状态： 0 未提交 1 审批中 2 审批退回 3 审批通过 4已失效.
   */
  @Enumerated(EnumType.ORDINAL)
  private ApprovalStatus approvalStatus;

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
  private java.time.LocalDateTime purchaseDate;

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
   * 部门.
   */
  private String departmentName;
  private String materialCode;
  private String materialName;
  private String materialSpecifications;
  private Long materialNumber;
  private String materialSize;
  private String materialUnit;
  private Long materialProcurementLeadTime;
  private String materialTypeName;
  private String materialTypeCode;
}
