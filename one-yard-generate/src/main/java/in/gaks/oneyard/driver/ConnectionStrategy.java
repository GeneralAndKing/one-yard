package in.gaks.oneyard.driver;


import java.util.List;

/**
 * @author BugRui
 * @date 2019/10/15 14:42
 **/
public interface ConnectionStrategy {

    /**
     * 返回数据库中的所有表名
     * @return List 表名
     */
    List<String> getTables();

    /**
     * 获取数据表的列信息
     * @param tableName 表名
     * @return 所有列信息
     */
    List<Column> getColumns(String tableName);

    /**
     *
     * @param mysqlFieldType 待转换mysql字段类型
     * @return java字段类型
     */
    String fieldConversion(String mysqlFieldType);

}
