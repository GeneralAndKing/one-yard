package in.gaks.oneyard.model.constant;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/4 下午9:59
 */
public final class SecurityConstants {

  private SecurityConstants() {
  }

  /**
   * 理验证码的 url 前缀.
   */
  public static final String VALIDATE_CODE_URL_PREFIX = "/code";

  /**
   * 手机验证码登录请求 url.
   */
  public static final String LOGIN_PROCESSING_URL_SMS = "/oauth/sms";

  /**
   * 需要验证短信验证码的注册请求 url.
   */
  public static final String REGISTER_PROCESSING_URL_EMAIL = "/auth/register";

  /**
   * 发送短信验证码或验证短信验证码时，手机的参数名称.
   */
  public static final String GRANT_TYPE_SMS = "sms";

  /**
   * 发送邮箱验证码或验证短信验证码时，邮箱的参数名称.
   */
  public static final String GRANT_TYPE_EMAIL = "email";

  /**
   * 公共角色.
   */
  public static final String ROLE_PUBLIC = "ROLE_PUBLIC";

  /**
   * 管理员角色.
   */
  public static final String ROLE_ADMIN = "ROLE_ADMIN";

  /**
   * 未授权角色.
   */
  public static final String ROLE_NO_AUTH = "ROLE_NO_AUTH";

  /**
   * 访客角色.
   */
  public static final String ROLE_ACCESS = "ROLE_ACCESS";

  /**
   * 登录角色.
   */
  public static final String ROLE_LOGIN = "ROLE_LOGIN";

  /**
   * 匿名用户.
   */
  public static final String ANONYMOUS_USER = "anonymousUser";


}
