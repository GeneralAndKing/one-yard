package in.gaks.oneyard.model.constant;

/**
 * 物料需求计划状态.
 *
 * @author Japoul
 * @date 2019/11/5 下午21:20
 */
public enum PlanStatus {
  /**
   * 自由.
   */
  FREE(0),
  /**
   * 提交审批.
   */
  APPRAVAL(1),
  /**
   * 提交至汇总.
   */
  SUMMARY(2),
  /**
   * 已删除.
   */
  DELETED(3),
  /**
   * 已终止.
   */
  FINALLY(4);

  private int value;

  PlanStatus(int status) {
    this.value = status;
  }

}
