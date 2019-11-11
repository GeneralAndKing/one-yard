package in.gaks.oneyard.driver;


import in.gaks.oneyard.properties.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author BugRui
 * @date 2019/10/16 14:06
 **/
@Slf4j
public class MssqlConnection implements ConnectionStrategy {

  public MssqlConnection(DataSourceProperties dataSourceProperties) {
    log.info("start initializing the mssql connection");
    try {
      Class.forName(dataSourceProperties.getDriverClassName());
      Connection connection = DriverManager
          .getConnection(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(),
              dataSourceProperties.getPassword());
      System.out.println(connection);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<String> getTables() {
    return null;
  }

  @Override
  public List<Column> getColumns(String tableName) {
    return null;
  }

  @Override
  public String fieldConversion(String mysqlFieldType) {
    return null;
  }


}
