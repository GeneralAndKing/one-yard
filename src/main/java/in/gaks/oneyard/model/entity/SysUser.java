package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.Status;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

  private String username;

  private String password;

  @Enumerated(EnumType.ORDINAL)
  private Status status = Status.NORMAL;

  private String icon;

  private String email;

  private String phone;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "sys_user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<SysRole> roles = new ArrayList<>();

}
