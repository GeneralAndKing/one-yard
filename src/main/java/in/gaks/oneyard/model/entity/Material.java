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
 * material.
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午10:17:20
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "material")
@Entity(name = "material")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class Material extends BaseEntity {
  /**
   * 物料编号.
   */
  private java.lang.String code;

  /**
   * 物料类别id.
   */
  private java.lang.Long typeId;

  /**
   * 物料库存数量.
   */
  private java.lang.Long number;

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
   * 物料采购提前期（天）.
   */
  private java.lang.Integer procurementLeadTime;

}
