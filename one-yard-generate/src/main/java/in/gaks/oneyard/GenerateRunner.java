package in.gaks.oneyard;

import in.gaks.oneyard.driver.Column;
import in.gaks.oneyard.driver.ConnectionStrategy;
import in.gaks.oneyard.gen.AbstractGen;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BugRui
 * @date 2019/10/27 14:48
 **/
@RequiredArgsConstructor
public class GenerateRunner implements ApplicationRunner {


    private final  ConnectionStrategy connection;
    private final Map<String, AbstractGen> beans;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> tables = connection.getTables();
        HashMap<String, List<Column>> tablesData = new HashMap<>();
        tables.forEach((tableName) -> {
            tablesData.put(tableName, connection.getColumns(tableName));
        });
        beans.forEach((k, bean) -> {
            bean.run(tablesData);
        });
    }
}
