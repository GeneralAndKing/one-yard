package in.gaks.oneyard.model.constant;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/11 下午5:15
 */
public enum NotificationStatus {
  /**
   * 未读.
   */
  UNREAD(0),
  /**
   * 已读.
   */
  READ(1);

  private int value;

  NotificationStatus(int status) {
    this.value = status;
  }
}
