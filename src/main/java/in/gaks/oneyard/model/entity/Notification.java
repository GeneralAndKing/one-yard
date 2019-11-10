package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import lombok.experimental.Accessors;
import lombok.*;
import javax.persistence.*;
import org.hibernate.annotations.Where;


/**
 * notification.
 *
 * @author BugRui EchoCow Japoul
 * @date 2019年11月10日 下午10:34:13
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Table(name = "notification")
@Entity(name = "notification")
@Where(clause = "is_enable = 1")
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {

  /**
   * 来源模块的路径（如：点此进行处理——模块路径+相应的参数）.
   */
  private java.lang.String source;

  /**
   * 通知的实际内容（***，您好！您关于***的申请被已经拒绝/接受；***，您好！有人邀请您介入***项目）.
   */
  private java.lang.String content;

  /**
   * 接收通知消息的模块路径.
   */
  private java.lang.String recModule;

  /**
   * 接收通知的用户编号.
   */
  private java.lang.Integer receiverId;

  /**
   * 是否已读 0未读 1已读.
   */
  private java.lang.Integer status;

}
