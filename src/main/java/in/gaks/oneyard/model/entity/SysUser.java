package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

/**
 * .用户
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午8:44
 */
@Data
@NoArgsConstructor
@Table(name = "sys_user")
@Entity(name = "sys_user")
@Accessors(chain = true)
@Where(clause = "is_enable = 1")
@ToString(callSuper = true, exclude = "roles")
@EqualsAndHashCode(callSuper = true, exclude = "roles")
public class SysUser extends BaseEntity implements Serializable {

  @NonNull
  private String username;

  @NonNull
  private String password;

  @NonNull
  @Enumerated(EnumType.ORDINAL)
  private Status status;

  @NonNull
  private String icon;

  @Email
  private String email;

  @NonNull
  private String phone;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "sys_user_role",
      joinColumns = @JoinColumn(name = "userId"),
      inverseJoinColumns = @JoinColumn(name = "roleId"))
  private Set<SysRole> roles = new HashSet<>();

}
