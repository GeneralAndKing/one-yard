package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午2:56
 */
@Data
@NoArgsConstructor
@Table(name = "sys_role_permission")
@Entity(name = "sys_role_permission")
@Accessors(chain = true)
@Where(clause = "is_enable = 1")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysRolePermission extends BaseEntity implements Serializable {

  private Long permissionId;

  private Long roleId;

}
