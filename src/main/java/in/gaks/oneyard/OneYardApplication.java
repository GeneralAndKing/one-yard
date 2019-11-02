package in.gaks.oneyard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * .启动类
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date
 */
@EnableJpaAuditing
@SpringBootApplication
public class OneYardApplication {

  public static void main(String[] args) {
    SpringApplication.run(OneYardApplication.class, args);
  }

}
