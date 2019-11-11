package in.gaks.oneyard.gen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import in.gaks.oneyard.driver.Column;
import in.gaks.oneyard.driver.ConnectionStrategy;
import in.gaks.oneyard.util.GenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @author BugRui
 * @date 2019/10/21 13:23
 **/
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractGen {

  /**
   * 通过lombok自动注入 如需要更多定义 可使用connection获取数据库的信息
   */
  private final Configuration configuration;
  private final ConnectionStrategy connection;

  /**
   * 定义templatePath
   *
   * @return template路径
   */
  abstract String templatePath();

  /**
   * 定义path 生成文件的路径
   *
   * @return path 路径
   */
  abstract String path();

  /**
   * 实现此方法
   *
   * @param tableData 数据库数据
   * @return 处理好的数据
   */
  abstract Map<String, Object> genData(Map.Entry<String, List<Column>> tableData);


  /**
   * 实现此方法
   *
   * @param tableName 表名
   * @return 文件名
   */
  abstract String genName(String tableName);


  /**
   * 实现此接口用来生成
   *
   * @param tablesData 获取到的数据库数据
   */
  public final void run(Map<String, List<Column>> tablesData) {
    String path = path();
    for (Map.Entry<String, List<Column>> tableData :
        tablesData.entrySet()
    ) {
      String name = genName(tableData.getKey());
      Map<String, Object> data = genData(tableData);
      File file = new File(String.format("%s/%s", path, name));
      try {
        GenUtil.createFile(file);
        Template freeMarkerTemplate = configuration.getTemplate(templatePath());
        freeMarkerTemplate.process(data, new FileWriter(file));
      } catch (TemplateException | IOException e) {
        log.error(e.getMessage());
      }

    }
  }
}
