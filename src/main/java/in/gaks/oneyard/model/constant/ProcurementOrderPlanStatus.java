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
   * 取消审批.
   */
  APPROVAL_CANCEL("取消审批"),
  /**
   * 已完成.
   */
  FINISHED("已完成"),
  /**
   * 已变更.
   */
  CHANGED("变更审批中"),
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
