package in.gaks.oneyard.driver;


import in.gaks.oneyard.properties.DataSourceProperties;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author BugRui
 * @date 2019/10/15 19:06
 **/
@Slf4j
public class MysqlConnection implements ConnectionStrategy {
    private Connection connection;
    private static HashMap<String, String> MYSQL_TO_JAVA=new HashMap<>();

    static {
        MYSQL_TO_JAVA.put("VARCHAR", "java.lang.String");
        MYSQL_TO_JAVA.put("BIGINT", "java.lang.Long");
        MYSQL_TO_JAVA.put("DATE", "java.time.LocalDate");
        MYSQL_TO_JAVA.put("FLOAT", "java.lang.Float");
        MYSQL_TO_JAVA.put("TINYINT", "java.lang.Integer");
        MYSQL_TO_JAVA.put("INT", "java.lang.Integer");
        MYSQL_TO_JAVA.put("BINARY", "java.lang.Byte");
        MYSQL_TO_JAVA.put("SMALLINT", "java.lang.Short");
        MYSQL_TO_JAVA.put("DATETIME", "java.time.LocalDateTime");
        MYSQL_TO_JAVA.put("BIT", "java.lang.Boolean");
        MYSQL_TO_JAVA.put("TEXT", "java.lang.String");
    }
    public MysqlConnection(DataSourceProperties dataSourceProperties) {
        log.info("Start initializing the mysql connection");
        try {
            Class.forName(dataSourceProperties.getDriverClassName());
            connection = DriverManager.getConnection(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
            connection.setCatalog(dataSourceProperties.getCatalog());
        } catch (ClassNotFoundException | SQLException e) {
            log.error("Can't connect to mysql");
            e.printStackTrace();
        }
    }

    @Override
    public String fieldConversion(String mysqlFieldType){
        return MYSQL_TO_JAVA.getOrDefault(mysqlFieldType,"Object");
    }


    @Override
    public List<String> getTables() {
        ArrayList<String> tables = new ArrayList<>();
        try {
            ResultSet resultSet = connection.getMetaData().getTables(connection.getCatalog(), null,
                    "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                tables.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    @Override
    public List<Column> getColumns(String tableName) {
        ArrayList<Column> columns = new ArrayList<>();
        try {
            ResultSet resultSet = connection.getMetaData().
                    getColumns(connection.getCatalog(), null, tableName, "%");
            while (resultSet.next()) {
                Column column = new Column();
                column.setName(resultSet.getString("COLUMN_NAME"));
                column.setComment(resultSet.getString("REMARKS"));
                column.setNullAble(resultSet.getBoolean("NULLABLE"));
                column.setSize(resultSet.getInt("COLUMN_SIZE"));
                column.setType(fieldConversion(resultSet.getString("TYPE_NAME")));
                columns.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

}
