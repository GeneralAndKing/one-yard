package in.gaks.oneyard.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云配置.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/15 上午10:01
 */
@Data
@Component
@ConfigurationProperties("application.qiniu")
public class QiniuProperties {

  /**
   * accessKey.
   */
  private String accessKey;
  /**
   * secretKey.
   */
  private String secretKey;
  /**
   * bucket.
   */
  private String bucket;
  /**
   * 地区.
   */
  private String area;
  /**
   * domain.
   */
  private String domain;
  /**
   * dirName.
   */
  private String dirName;
  /**
   * 域名.
   */
  private String host;
}
