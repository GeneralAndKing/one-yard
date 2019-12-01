package in.gaks.oneyard.model.constant;

/**
 * 采购订单状态.
 *
 * @author Japoul
 * @date 2019/11/30 下午21:20
 */

public enum ProcurementOrderPlanStatus {
  /**
   * 待提交.
   */
  NO_SUBMIT("待提交"),
  /**
   * 提交审批.
   */
  APPROVAL("提交审批"),
  /**
   * 已关闭.
   */
  CLOSE("已关闭"),
  /**
   * 未完成.
   */
  UNFINISHED("未完成"),
  /**
   * 已完成.
   */
  FINISHED("已完成"),
  /**
   * 已变更.
   */
  CHANGED("已变更"),
  /**
   * 已生效.
   */
  EFFECTIVE("已生效"),
  /**
   * 已作废.
   */
  CANCEL("已作废");

  private String value;

  ProcurementOrderPlanStatus(String status) {
    this.value = status;
  }

}
