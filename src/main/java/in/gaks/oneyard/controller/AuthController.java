package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import in.gaks.oneyard.model.entity.SysUser;
import in.gaks.oneyard.model.exception.ResourceExistException;
import in.gaks.oneyard.model.helper.ApplicationProperties;
import in.gaks.oneyard.model.helper.VerifyParameter;
import in.gaks.oneyard.service.AuthService;
import in.gaks.oneyard.util.CodeUtil;
import in.gaks.oneyard.util.ParamValidator;
import java.time.Duration;
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
  private final ApplicationProperties applicationProperties;

  @VerifyParameter(required = {"username|#用户名为必填项",
      "email#邮箱为必填项",
      "phone#手机号为必填项",
      "password#密码为必填项",
      "rePassword#确认密码为必填项",
      "code#确认密码为必填项"
  }, size = {
      "phone|11-11#手机号长度只能为11位",
      "code|4-4#验证码长度只能为4位",
      "password|8-18#密码长度应该在8-18之间",
      "rePassword|8-18#确认密码长度应该在8-18之间"
  })
  @PostMapping("/register/create")
  public HttpEntity<?> register(@NotNull @RequestBody JSONObject user) {
    Assert.isTrue(user.getString("password").equals(user.getString("rePassword")),
        "两次密码不一致");
    SysUser sysUser = user.toJavaObject(SysUser.class);
    ValueOperations<String, String> operation = redisTemplate.opsForValue();
    String code = operation.get(applicationProperties.registerKey(sysUser.getEmail()));
    Assert.notNull(code, "验证码发送失败或已失效，请重新发送。");
    Assert.isTrue(user.getString("code").equals(code), "验证码错误");
    authService.register(sysUser);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * 发送注册邮件.
   *
   * @param email 注册邮箱
   * @return 响应
   */
  @GetMapping("/register/code/{email}")
  public HttpEntity<?> registerCode(@PathVariable String email) {
    Assert.isTrue(ParamValidator.checkEmail(email), "邮箱格式不合法");
    if (authService.existByEmail(email)) {
      throw new ResourceExistException("用户 %s 已经存在", email);
    }
    ValueOperations<String, String> operation = redisTemplate.opsForValue();
    String code = CodeUtil.random(applicationProperties.getRegisterCodeLength());
    // TODO: 邮件发送
    operation.set(applicationProperties.registerKey(email), code,
        Duration.ofMinutes(applicationProperties.getRegisterCodeValidityMinute()));
    return ResponseEntity.ok().build();
  }

}
