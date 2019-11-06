package in.gaks.oneyard.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2019/11/5 下午8:18
 */
public final class ParamValidator {

  private ParamValidator() {
  }

  /**
   * 邮箱匹配.
   *
   * <ul>
   *   <li>" \w"：匹配字母、数字、下划线。等价于'[A-Za-z0-9_]'</li>
   *   <li>"|"  : 或的意思，就是二选一</li>
   *   <li>"*" : 出现0次或者多次</li>
   *   <li>"+" : 出现1次或者多次</li>
   *   <li>"{n,m}" : 至少出现n个，最多出现m个</li>
   *   <li>"$" : 以前面的字符结束</li>
   * </ul>
   *
   * @param content 内容
   * @return 结果
   */
  public static boolean checkEmail(String content) {
    String emailPattern = "^\\w+((-\\w+)|(\\.\\w+))*@\\w+(\\.\\w{2,3}){1,3}$";
    Pattern p = Pattern.compile(emailPattern);
    Matcher matcher = p.matcher(content);
    return matcher.matches();
  }
}
