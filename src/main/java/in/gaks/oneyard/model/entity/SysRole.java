package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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

  @OneToOne
  private SysDepartment department;

}
