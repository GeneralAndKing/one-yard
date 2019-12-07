package in.gaks.oneyard.model.constant;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/12/7 下午10:39
 */
public enum ProcurementMaterialStatus {
  /**
   * 正常.
   */
  NORMAL("正常"),
  /**
   * 变更
   */
  CHANGED("变更"),
  /**
   * 完成
   */
  FINISHED("完成");

  private String value;

  ProcurementMaterialStatus(String status) {
    this.value = status;
  }

}
