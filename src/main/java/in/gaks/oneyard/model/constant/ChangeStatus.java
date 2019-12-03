package in.gaks.oneyard.model.constant;

/**
 * 变更信息状态.
 *
 * @author Japoul
 * @date 2019/12/3 上午9:17
 */
public enum ChangeStatus {
  /**
   * 待审批.
   */
  APPROVAL_ING("待审批"),
  /**
   * 审核通过.
   */
  APPROVAL_OK("审核通过");

  private String value;

  ChangeStatus(String status) {
    this.value = status;
  }
}
