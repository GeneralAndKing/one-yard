package in.gaks.oneyard.model.constant;

/**
 * 采购订单审批状态.
 *
 * @author Japoul
 * @date 2019/12/7 下午21:20
 */
public enum ProcurementApprovalStatus {
  /**
   * 未提交.
   */
  NO_SUBMIT("未提交"),
  /**
   * 审批中.
   */
  APPROVAL_ING("审批中"),
  /**
   * 审批通过.
   */
  APPROVAL_OK("审批通过"),
  /**
   * 审批退回.
   */
  APPROVAL_NO("审批退回");

  private String value;

  ProcurementApprovalStatus(String status) {
    this.value = status;
  }

}
