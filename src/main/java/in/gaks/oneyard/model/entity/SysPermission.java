package in.gaks.oneyard.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.HttpMethod;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Accessors(chain = true)
@Table(name = "sys_permission")
@Entity(name = "sys_permission")
@Where(clause = "is_enable = 1")
@ToString(callSuper = true, exclude = "departments")
@EqualsAndHashCode(callSuper = true, exclude = "departments")
public class SysPermission extends BaseEntity {

  private String description;

  private String matchUrl;

  @Enumerated(EnumType.STRING)
  private HttpMethod method;

  @JsonBackReference
  @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
  private Set<SysDepartment> departments = new HashSet<>();

}
