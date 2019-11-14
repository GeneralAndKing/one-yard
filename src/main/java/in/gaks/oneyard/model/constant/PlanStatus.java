package in.gaks.oneyard.model.constant;

/**
 * 计划状态.
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
  APPROVAL(1),
  /**
   * 提交至汇总.
   */
  SUMMARY(2),
  /**
   * 已删除.
   */
  DELETED(3),
  /**
   *采购主管审批通过.
   */
  PROCUREMENT_OK(4),
  /**
   * 已进入下一阶段.
   */
  FINALLY(5);

  private int value;

  PlanStatus(int status) {
    this.value = status;
  }

}
