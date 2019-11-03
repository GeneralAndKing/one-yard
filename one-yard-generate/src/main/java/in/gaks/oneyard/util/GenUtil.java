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
    private static final Pattern COMPILE = Pattern.compile("_([a-z])");

    /**
     * 下划线转驼峰式
     *
     * @param underLine 待转换字符串
     * @return 驼峰式字符串
     */
    public static String underLineToHump(String underLine) {
        Matcher matcher = COMPILE.matcher(underLine);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
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
