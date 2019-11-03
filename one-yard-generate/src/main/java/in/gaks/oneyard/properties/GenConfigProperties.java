package in.gaks.oneyard.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author BugRui
 * @date 2019/10/15 14:18
 **/
@Data
@ConfigurationProperties(prefix = "spring.gen")
public class GenConfigProperties {

    private DataSourceProperties dataSource;

    private Boolean underLineToHump=true;

    private Boolean firstCharUpperCase=true;

    private String suffix="java";

}
