package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

/**
 * 部门.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/3 下午12:05
 */
@Data
@NoArgsConstructor
@Table(name = "sys_department")
@Where(clause = "is_enable = 1")
@Entity(name = "sys_department")
@Accessors(chain = true)
@ToString(callSuper = true, exclude = "permissions")
@EqualsAndHashCode(callSuper = true, exclude = "permissions")
public class SysDepartment extends BaseEntity {

  private String description;


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "sys_department_permission",
      joinColumns = @JoinColumn(name = "departmentId"),
      inverseJoinColumns = @JoinColumn(name = "permissionId"))
  private Set<SysPermission> permissions = new HashSet<>();

}
