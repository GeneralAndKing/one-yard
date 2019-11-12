package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceExistException;
import in.gaks.oneyard.model.exception.ResourceNotFoundException;
import in.gaks.oneyard.model.exception.ValidationException;
import in.gaks.oneyard.model.helper.VerifyParameter;
import in.gaks.oneyard.model.properties.CodeProperties;
import in.gaks.oneyard.service.AuthService;
import in.gaks.oneyard.util.CodeUtil;
import in.gaks.oneyard.util.EmailUtils;
import in.gaks.oneyard.util.ParamValidator;
import java.time.Duration;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权控制器.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/5 下午6:05
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final RedisTemplate<String, String> redisTemplate;
  private final CodeProperties codeProperties;
  private final EmailUtils emailUtils;
  private static final String PASSWORD = "password";
  private static final String RE_PASSWORD = "rePassword";
  private static final String CODE = "code";
  private static final String EMAIL = "email";
  private static final String ERROR_MESSAGE = "验证码错误";

  /**
   * 注册第一步：发送注册邮件.
   *
   * @param email 注册邮箱
   * @return 响应
   */
  @GetMapping("/register/{email}")
  public HttpEntity<?> registerCode(@PathVariable String email) {
    if (authService.existByEmail(email)) {
      throw new ResourceExistException("用户 %s 已经存在", email);
    }
    sendCode(email, "注册", "欢迎注册", codeProperties.registerKey(email));
    return ResponseEntity.ok().build();
  }

  /**
   * 注册第二步：注册.
   *
   * @param user 用户
   * @return 结果
   */
  @VerifyParameter(
      required = {"username#用户名为必填项",
          "email#邮箱为必填项",
          "phone#手机号为必填项",
          "password#密码为必填项",
          "rePassword#确认密码为必填项",
          "code#验证码为必填项"},
      size = {"phone|11-11#手机号长度只能为11位",
          "password|8-18#密码长度应该在8-18之间",
          "rePassword|8-18#确认密码长度应该在8-18之间"
      }
  )
  @PostMapping("/register")
  public HttpEntity<?> register(@NotNull @RequestBody JSONObject user) {
    Assert.isTrue(user.getString(PASSWORD).equals(user.getString(RE_PASSWORD)), "两次密码不一致");
    SysUser sysUser = user.toJavaObject(SysUser.class);
    if (errorCode(codeProperties.registerKey(sysUser.getEmail()), user.getString(CODE))) {
      throw new ValidationException(ERROR_MESSAGE);
    }
    authService.register(sysUser);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 找回密码第一步： 找回密码发送验证码.
   *
   * @param email 找回邮箱
   * @return 结果
   */
  @GetMapping("/forget/{email}")
  public HttpEntity<?> forgetCode(@PathVariable String email) {
    if (!authService.existByEmail(email)) {
      throw new ResourceNotFoundException("用户 %s 不存在", email);
    }
    sendCode(email, "找回密码", "找回密码", codeProperties.forgetKey(email));
    return ResponseEntity.ok().build();
  }

  /**
   * 找回密码第二步：找回密码验证邮箱.
   *
   * @param email 邮箱
   * @param code  验证码
   * @return 响应
   */
  @GetMapping("/forget/{email}/{code}")
  public HttpEntity<?> forgetValidate(@PathVariable String email, @PathVariable String code) {
    if (errorCode(codeProperties.forgetKey(email), code)) {
      throw new ValidationException(ERROR_MESSAGE);
    }
    return ResponseEntity.ok().build();
  }

  /**
   * 找回密码第三步：找回密码修改密码.
   *
   * @param params 参数
   * @return 响应
   */
  @VerifyParameter(
      required = {"email#邮箱为必填项",
          "password#密码为必填项",
          "rePassword#确认密码为必填项",
          "code#验证码为必填项"},
      size = {
          "password|8-18#密码长度应该在8-18之间",
          "rePassword|8-18#确认密码长度应该在8-18之间"
      }
  )
  @PostMapping("/forget")
  public HttpEntity<?> forget(@NotNull @RequestBody JSONObject params) {
    Assert.isTrue(params.getString(PASSWORD).equals(params.getString(RE_PASSWORD)), "两次密码不一致");
    if (errorCode(codeProperties.forgetKey(params.getString(EMAIL)), params.getString(CODE))) {
      throw new ValidationException(ERROR_MESSAGE);
    }
    authService.modifyPassword(params.getString(EMAIL), params.getString(PASSWORD));
    return ResponseEntity.ok().build();
  }

  /**
   * 发送邮件.
   *
   * @param email   email
   * @param type    类型名称
   * @param subject 主题
   * @param key     存在缓存中的 key
   */
  private void sendCode(String email, String type, String subject, String key) {
    Assert.isTrue(ParamValidator.checkEmail(email), "邮箱格式不合法");
    String code = CodeUtil.random(codeProperties.getEmailCodeLength());
    HashMap<String, Object> params = Maps.newHashMap();
    params.put("time", codeProperties.getEmailCodeValidityMinute());
    params.put(CODE, code);
    log.info("向 {} 发送 {} 邮件验证码：{}", email, type, code);
    if (codeProperties.getEmailEnable()) {
      emailUtils.sendTemplateMail(email, type, subject, "CodeTemplate.html", params);
    }
    ValueOperations<String, String> operation = redisTemplate.opsForValue();
    operation
        .set(key, code, Duration.ofMinutes(codeProperties.getEmailCodeValidityMinute()));
  }

  /**
   * 验证.
   *
   * @param key  缓存中的 key
   * @param code 验证码
   * @return 结果
   */
  private boolean errorCode(@NotNull String key, @NotNull String code) {
    ValueOperations<String, String> operation = redisTemplate.opsForValue();
    String validate = operation.get(key);
    Assert.notNull(validate, "验证码发送失败或已失效，请重新验证。");
    return !code.equals(validate);
  }

}
