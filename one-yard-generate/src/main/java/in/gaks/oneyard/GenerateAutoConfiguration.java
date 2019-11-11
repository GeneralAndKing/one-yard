package in.gaks.oneyard;

import in.gaks.oneyard.driver.ConnectionStrategy;
import in.gaks.oneyard.driver.MssqlConnection;
import in.gaks.oneyard.driver.MysqlConnection;
import in.gaks.oneyard.gen.AbstractGen;
import in.gaks.oneyard.properties.DataSourceProperties;
import in.gaks.oneyard.properties.GenConfigProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * @author BugRui
 * @date 2019/10/27 14:39
 **/
@Configuration
@ConditionalOnNotWebApplication
@SuppressWarnings({"all"})
@EnableConfigurationProperties(GenConfigProperties.class)
public class GenerateAutoConfiguration {

  private static final String MYSQL = "com.mysql";
  private static final String MSSQL = "com.microsoft.sqlserver";

  @Bean
  public GenerateRunner generateRunner(Map<String, AbstractGen> beans,
      ConnectionStrategy connection) {
    return new GenerateRunner(connection, beans);
  }

  @Bean
  public ConnectionStrategy mysqlConnect(GenConfigProperties genConfigProperties) {
    DataSourceProperties dataSource = genConfigProperties.getDataSource();
    ConnectionStrategy connection = null;
    if (dataSource.getDriverClassName().contains(MYSQL)) {
      connection = new MysqlConnection(dataSource);
    } else if (dataSource.getDriverClassName().contains(MSSQL)) {
      connection = new MssqlConnection(dataSource);
    }
    return connection;
  }


}
