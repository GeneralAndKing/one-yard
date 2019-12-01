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
 * order_terms.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月30日 下午8:05:18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "order_terms")
@Entity(name = "order_terms")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class OrderTerms extends BaseEntity {

  /**
   * 条款编号.
   */
  private java.lang.String code;

  /**
   * 订单id.
   */
  private java.lang.Long orderId;

  /**
   * 条款类型.
   */
  private java.lang.String type;

  /**
   * 条款内容.
   */
  private java.lang.String content;

  /**
   * 说明.
   */
  private java.lang.String description;

}
