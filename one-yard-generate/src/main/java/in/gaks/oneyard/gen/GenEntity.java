package in.gaks.oneyard.gen;

import freemarker.template.Configuration;
import in.gaks.oneyard.driver.Column;
import in.gaks.oneyard.driver.ConnectionStrategy;
import in.gaks.oneyard.util.GenUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



/**
 * @author BugRui
 * @date 2019/10/21 13:24
 **/
@Component
public class GenEntity extends AbstractGen {


    public GenEntity(Configuration configuration, ConnectionStrategy connection) {
        super(configuration, connection);
    }

    @Override
    String templatePath() {
        return "entity.ftl";
    }

    @Override
    String path() {
      return "./src/main/java/in/gaks/oneyard/model/entity";
    }

    @Override
    String genName(String tableName) {
        return GenUtil.firstCharChange(GenUtil.underLineToHump(tableName))+".java";
    }

    @Override
    Map<String, Object> genData(Map.Entry<String, List<Column>> tableData) {
      String[] noGen = new String[]{"id", "name", "createUser", "createTime", "modifyUser",
          "modifyTime",
          "isEnable", "sort", "remark"};
        String tableName = tableData.getKey();
        List<Column> columns = tableData.getValue();
        HashMap<String, Object> data = new HashMap<>();
        //转换className;
        String className = GenUtil.underLineToHump(tableName);
        Iterator<Column> iterator = columns.iterator();
        while (iterator.hasNext()){
            Column column = iterator.next();
            column.setName(GenUtil.underLineToHump(column.getName()));
            for (String no :
                    noGen) {
                if (no.equals(column.getName())){
                    iterator.remove();
                    break;
                }
            }
        }
        className= GenUtil.firstCharChange(className);
      data.put("PackageName", "in.gaks.oneyard.model.entity;");
        data.put("TableName",tableName);
        data.put("ClassName",className);
        data.put("Columns",columns);
        return data;
    }


}
