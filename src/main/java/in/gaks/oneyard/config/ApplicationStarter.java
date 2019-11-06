package in.gaks.oneyard.config;

import in.gaks.oneyard.model.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestProperties;
import org.springframework.stereotype.Component;

/**
 * 应用启动.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/5 下午6:38
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStarter implements ApplicationRunner {

  private final SecurityProperties securityProperties;
  private final RepositoryRestProperties repositoryRestProperties;

  @Value("${server.servlet.context-path}")
  private String apiBasePath;

  @Override
  public void run(ApplicationArguments args) {
    if (securityProperties.getEnable()) {
      log.info("Spring security started! Client is : {}", securityProperties.getClientId());
    } else {
      log.info("Spring security did not start!");
    }
    log.info("Api base path is: {}.", apiBasePath);
    log.info("Rest base path is: {}{}.", apiBasePath, repositoryRestProperties.getBasePath());
  }

}
