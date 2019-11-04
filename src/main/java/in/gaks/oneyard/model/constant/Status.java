package in.gaks.oneyard.model.constant;

/**
 * 用户状态.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/2 上午9:17
 */
public enum Status {
  /**
   * 正常.
   */
  NORMAL(1),
  /**
   * 锁定.
   */
  LOCKED(0);

  private int value;

  Status(int status) {
    this.value = status;
  }

  public static boolean isNormal(Status status) {
    return NORMAL == status;
  }
}
