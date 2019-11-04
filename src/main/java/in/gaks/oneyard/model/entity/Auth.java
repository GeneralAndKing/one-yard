package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * auth
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午5:22:07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "auth")
@Entity(name = "auth")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class Auth extends BaseEntity {

  /**
   *
   */
  private java.lang.String name;

  /**
   * 审批类型 0 物料需求计划审批 1 采购计划审批
   */
  private java.lang.Integer authType;

  /**
   * 需求计划汇总表id
   */
  private java.lang.Integer planId;

  /**
   * 审批意见（结果） （审批退回 审批通过）
   */
  private java.lang.String result;

  /**
   * 说明（对审批结果的解释）
   */
  private java.lang.String description;
}
