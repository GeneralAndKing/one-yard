package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * sys_user_role
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午5:22:07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "sys_user_role")
@Entity(name = "sys_user_role")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {

  /**
   *
   */
  private java.lang.String name;

  /**
   *
   */
  private java.lang.Integer userId;

  /**
   *
   */
  private java.lang.Integer roleId;
}
