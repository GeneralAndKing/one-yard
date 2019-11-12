package in.gaks.oneyard.model.entity;

import in.gaks.oneyard.base.BaseEntity;
import in.gaks.oneyard.model.constant.NotificationStatus;
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
   * 通知的实际内容.
   */
  private java.lang.String content;

  /**
   * 接收通知的用户编号.
   */
  private java.lang.Long receiverId;

  /**
   * 是否已读 0未读 1已读.
   */
  @Enumerated(EnumType.ORDINAL)
  private NotificationStatus status = NotificationStatus.UNREAD;

}
