package in.gaks.oneyard.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BugRui
 * @date 2019/10/15 19:46
 **/
@Component
public class GenUtil {

  private static final String UNDERLINE = "_";
    private static final Pattern COMPILE = Pattern.compile("_([a-z])");

    /**
     * 下划线转驼峰式
     *
     * @param para 待转换字符串
     * @return 驼峰式字符串
     */
    public static String underLineToHump(String para) {
      StringBuilder result = new StringBuilder();
      for (String s : para.split(UNDERLINE)) {
        if (!para.contains("_")) {
          result.append(s);
          continue;
        }
        if (result.length() == 0) {
          result.append(s.toLowerCase());
        } else {
          result.append(s.substring(0, 1).toUpperCase());
          result.append(s.substring(1).toLowerCase());
        }
      }
      return result.toString();
    }

    /**
     * 首字母大写
     *
     * @param str 待转换字符串
     * @return 转换后字符串
     */
    public static String firstCharChange(String str) {
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] -= 32;
        }
        return String.valueOf(chars);
    }

    public static void createFile(File file) throws IOException {
        if(!file.exists()){
            if((!file.getParentFile().exists())&&(!file.getParentFile().mkdir())){
                throw new IOException("Can't create file path");
            }
            if(!file.createNewFile()){
                throw new IOException("Can't create file");
            }
        }
    }

}
