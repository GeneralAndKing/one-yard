package in.gaks.oneyard.driver;

import lombok.Data;

/**
 * @author BugRui
 * @date 2019/10/16 21:16
 **/
@Data
public class Column {

  private String name;
  private String type;
  private Integer size;
  private Boolean nullAble;
  private String comment;


}
