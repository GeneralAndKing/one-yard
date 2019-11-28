package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
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
 * @date 2019/11/28 下午5:03
 */
@Data
@NoArgsConstructor
@Table(name = "sys_log")
@Where(clause = "is_enable = 1")
@Entity(name = "sys_log")
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysLog extends BaseEntity {

  /**
   * 请求链接.
   */
  private String url;

  /**
   * 来源地址.
   */
  private String source;

  /**
   * 浏览器.
   */
  private String browser;

  /**
   * 请求方法.
   */
  private String method;

  /**
   * 请求参数.
   */
  private String args;

  /**
   * 请求结果.
   */
  private String result;

}
