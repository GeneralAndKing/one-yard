package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

/**
 * 角色.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 下午5:04
 */
@Data
@NoArgsConstructor
@Table(name = "sys_role")
@Entity(name = "sys_role")
@Accessors(chain = true)
@ToString(callSuper = true)
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

  private String description;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private SysDepartment department;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "sys_role_permission",
      joinColumns = @JoinColumn(name = "roleId"),
      inverseJoinColumns = @JoinColumn(name = "permissionId"))
  private Set<SysPermission> rolePermissions = new HashSet<>();

}
