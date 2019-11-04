package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * sys_user
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月4日 下午5:22:07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "sys_user")
@Entity(name = "sys_user")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

  /**
   * 名称
   */
  private java.lang.String name;

  /**
   * 用户名
   */
  private java.lang.String username;

  /**
   * 密码
   */
  private java.lang.String password;

  /**
   * 状态 1、启用 0、禁用
   */
  private java.lang.Integer status;

  /**
   * 头像
   */
  private java.lang.String icon;

  /**
   * 电子邮箱
   */
  private java.lang.String email;

  /**
   * 手机号
   */
  private java.lang.String phone;
}
