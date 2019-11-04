package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * sys_permission
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午5:22:07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "sys_permission")
@Entity(name = "sys_permission")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {

  /**
   *
   */
  private java.lang.String name;

  /**
   *
   */
  private java.lang.String description;

  /**
   * URL 匹配
   */
  private java.lang.String matchUrl;

  /**
   * 允许通过的方法：GET POST PUT DELETE ALL
   */
  private java.lang.String method;
}
