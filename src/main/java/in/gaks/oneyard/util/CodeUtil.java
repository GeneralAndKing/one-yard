package in.gaks.oneyard.util;

import java.util.Random;

/**
 * 验证码生成工具类.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/6 下午1:18
 */
public final class CodeUtil {
  private CodeUtil() {
  }

  private static final char[] MORE_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private static final Random RANDOM = new Random();

  /**
   * 随机生成验证码.
   *
   * @param length 长度
   * @param end    结束长度
   * @return 结果
   */
  private static String random(Integer length, Integer end) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < length; i++) {
      result.append(MORE_CHAR[RANDOM.nextInt(end)]);
    }
    return result.toString();
  }

  /**
   * 随机生成验证码.
   *
   * @param length  长度
   * @param onlyNum 是否只要数字
   * @return 结果
   */
  public static String random(Integer length, Boolean onlyNum) {
    return onlyNum ? random(length, 10) : random(length, MORE_CHAR.length);
  }

  /**
   * 随机验证码.
   *
   * @param length 长度
   * @return 结果
   */
  public static String random(Integer length) {
    return random(length, false);
  }
}
