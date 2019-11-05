package in.gaks.oneyard.model.constant;

/**
 * 物料需求计划状态.
 *
 * @author Japoul
 * @date 2019/11/5 下午21:20
 */
public enum ApprovalStatus {
  /**
   * 未提交.
   */
  NO_SUBMIT(0),
  /**
   * 审批中.
   */
  APPRAVAL(1),
  /**
   * 审批退回.
   */
  SUMMARY(2),
  /**
   * 审批通过.
   */
  DELETED(3);

  private int value;

  ApprovalStatus(int status) {
    this.value = status;
  }

}
