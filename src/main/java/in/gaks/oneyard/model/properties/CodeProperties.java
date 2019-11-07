package in.gaks.oneyard.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午1:29
 */
@Data
@Component
@ConfigurationProperties("application.code")
public class CodeProperties {

  /**
   * 缓存中注册验证码的后最.
   */
  private String registerCodeKeySuffix = "@register";

  /**
   * 缓存中找回密码验证码的后最.
   */
  private String forgetCodeKeySuffix = "@forget";

  /**
   * 邮件验证码长度.
   */
  private Integer emailCodeLength = 4;

  /**
   * 是否真的发送邮件.
   */
  private Boolean emailEnable = true;

  /**
   * 邮件验证码有效期.
   */
  private Long emailCodeValidityMinute = 15L;

  /**
   * 构建注册验证码的 key.
   *
   * @param email 邮箱号
   * @return 结果
   */
  public String registerKey(String email) {
    return email + registerCodeKeySuffix;
  }

  /**
   * 构建找回密码验证码的 key.
   *
   * @param email 邮箱号
   * @return 结果
   */
  public String forgetKey(String email) {
    return email + forgetCodeKeySuffix;
  }

}
