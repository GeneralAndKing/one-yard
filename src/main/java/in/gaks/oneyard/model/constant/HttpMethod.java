package in.gaks.oneyard.model.constant;

import org.jetbrains.annotations.NotNull;

/**
 * http method.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/3 下午12:33
 */
public enum HttpMethod {
  /**
   * GET 方法.
   */
  GET,
  /**
   * POST 方法.
   */
  POST,
  /**
   * PUT 方法.
   */
  PUT,
  /**
   * PATCH 方法.
   */
  PATCH,
  /**
   * DELETE 方法.
   */
  DELETE,
  /**
   * 所有方法.
   */
  ALL;

  /**
   * 方法匹配.
   *
   * @param name 匹配的方法
   * @return 结果
   */
  public boolean match(@NotNull String name) {
    return name().equalsIgnoreCase(name);
  }

  public boolean match(@NotNull HttpMethod method) {
    return method.name().equals(name());
  }
}
