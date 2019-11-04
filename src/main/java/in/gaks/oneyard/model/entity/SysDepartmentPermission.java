package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * sys_department_permission
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午5:22:07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "sys_department_permission")
@Entity(name = "sys_department_permission")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class SysDepartmentPermission extends BaseEntity {

  /**
   * 名称
   */
  private java.lang.String name;

  /**
   * 部门
   */
  private java.lang.Integer departmentId;

  /**
   * 权限
   */
  private java.lang.Integer permissionId;
}
