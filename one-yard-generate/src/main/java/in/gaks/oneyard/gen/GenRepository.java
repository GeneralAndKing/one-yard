package in.gaks.oneyard.gen;

import freemarker.template.Configuration;
import in.gaks.oneyard.driver.Column;
import in.gaks.oneyard.driver.ConnectionStrategy;
import in.gaks.oneyard.util.GenUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author BugRui
 * @date 2019/10/21 20:01
 **/
@Component
public class GenRepository extends AbstractGen {
    public GenRepository(Configuration configuration, ConnectionStrategy connection) {
        super(configuration, connection);
    }

    @Override
    String templatePath() {
        return "repository.ftl";
    }

    @Override
    String path() {
      return "./src/main/java/in/gaks/oneyard/repository";
    }

    @Override
    Map<String, Object> genData(Map.Entry<String, List<Column>> tableData) {
        Map<String, Object> data = new HashMap<>();
      String path = tableData.getKey().toUpperCase();
        String entityNameLow = GenUtil.underLineToHump(tableData.getKey());
        String entityName = GenUtil.firstCharChange(entityNameLow);
        data.put("EntityName", entityName);
        data.put("EntityNameLow", entityNameLow);
      data.put("path", path);
        return data;
    }

    @Override
    String genName(String tableName) {
        return GenUtil.firstCharChange(GenUtil.underLineToHump(tableName)) + "Repository.java";
    }
}
