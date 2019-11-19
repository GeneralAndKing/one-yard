package in.gaks.oneyard.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/18 下午11:07
 */
@Slf4j
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

  private final RedisTemplate<String, String> redisTemplate;

  /**
   * 仪表盘查询 api 请求数量.
   *
   * @return 结果
   */
  @GetMapping("/api")
  public HttpEntity<?> apiNumber() {
    return redisNumber("api-num=*");
  }

  /**
   * 授权次数.
   *
   * @return 结果
   */
  @GetMapping("/auth")
  public HttpEntity<?> authNumber() {
    return redisNumber("auth-num=*");
  }

  /**
   * 获取指定keys的大小.
   *
   * @param keysName keys
   * @return 结果
   */
  private HttpEntity<?> redisNumber(String keysName) {
    ListOperations<String, String> operation = redisTemplate.opsForList();
    Set<String> keys = operation.getOperations().keys(keysName);
    if (Objects.isNull(keys)) {
      keys = Sets.newConcurrentHashSet();
    }
    TreeSet<String> sortKeys = Sets.newTreeSet(keys);
    JSONObject result = new JSONObject();
    for (String key : sortKeys) {
      result.put(StringUtils.substringAfterLast(key, "="), operation.size(key));
    }
    return ResponseEntity.ok(result);
  }
}
