package in.gaks.oneyard.model.constant;

/**
 * 审批类别.
 *
 * @author Japoul
 * @date 2019/11/12 下午14:20
 */
public enum ApprovalTypeStatus {
  /**
   * 物料需求计划审批.
   */
  MATERIAL_APPROVAL(0),
  /**
   * 采购计划第一次审批.
   */
  PROCUREMENT_APPROVAL(1);

  private int value;

  ApprovalTypeStatus(int status) {
    this.value = status;
  }

}
