package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.HttpMethod;
import in.gaks.oneyard.model.constant.Status;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Where;

/**
 * 权限表.
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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {

  private String description;

  private String matchUrl;

  @Enumerated(EnumType.ORDINAL)
  private Status status;

  @Enumerated(EnumType.STRING)
  private HttpMethod method;

}
