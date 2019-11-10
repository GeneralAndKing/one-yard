package in.gaks.oneyard.model.constant;

/**
 * 用户状态.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:17
 */
public enum MaterialStatus {
  /**
   * 初始.
   */
  INIT(0),
  /**
   * 退回.
   */
  BACK(1);

  private int value;

  MaterialStatus(int status) {
    this.value = status;
  }
}
