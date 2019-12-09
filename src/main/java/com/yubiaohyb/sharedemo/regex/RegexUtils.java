package com.yubiaohyb.sharedemo.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/6 19:20
 */
public final class RegexUtils {

    public static boolean isTelephone(String tel) {
        if (StringUtils.isBlank(tel)) {
            return false;
        }
        return tel.matches("^0\\d{3}-[1-9]\\d{5,7}$");
    }

    public static String[] split1(String value, String splitor) {
        return value.split(splitor);
    }

    //上面代码内部，实际封装的就是下面的内容，因此可以对pattern进行常量化处理

    public static String[] split2(String value, String splitor) {
        Pattern PATTERN = Pattern.compile(splitor);
        return PATTERN.split(value);
    }

    public static List<String> find(String value, String pattern) {
        Pattern PATTERN = Pattern.compile(pattern);
        Matcher matcher = PATTERN.matcher(value);
        List<String> list = new ArrayList<>();
        while(matcher.find()) {
            list.add(value.substring(matcher.start(), matcher.end()));
        }
        return list;
    }

    public static String replaceAll(String value, String pattern, String replacement) {
        return value.replaceAll(pattern, replacement);
    }

    public static List<String> getGroupList(String value, String pattern) {
        Pattern PATTERN = Pattern.compile(pattern);
        Matcher matcher = PATTERN.matcher(value);
        List<String> list = new ArrayList<>();
        if(matcher.matches()) {
            for(int i=1; i<=matcher.groupCount(); i++) {
                list.add(matcher.group(i));
            }
        }
        return list;
    }
}
