package in.gaks.oneyard.model.constant;

/**
 * 需求物料状态.
 *
 * @author Japoul
 * @date 2019/11/20 上午9:17
 */
public enum MaterialStatus {
  /**
   * 初始.
   */
  INIT(0),
  /**
   * 退回.
   */
  BACK(1),
  /**
   * 合并.
   */
  MERGE(2),
  /**
   * 拆分
   */
  SPLIT(3),
  /**
   * 库存
   */
  INVENTORY(4);
  private int value;

  MaterialStatus(int status) {
    this.value = status;
  }
}
