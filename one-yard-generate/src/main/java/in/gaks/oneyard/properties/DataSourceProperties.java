package in.gaks.oneyard.properties;

import lombok.Data;

/**
 * @author BugRui
 * @date 2019/10/16 23:05
 **/
@Data
public class DataSourceProperties {

  private String url;
  private String username;
  private String password;
  private String driverClassName;
  private String catalog;
}
