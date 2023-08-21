package com.yubiaohyb.sharedemo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2022/10/28 15:09
 */
public class TempTest {
    private static final Pattern richTextPattern = Pattern.compile("^<[a-z]+[\\d\\D]+</[a-z]+>$");

    public static void main(String[] args) {
        String text = "<p>123123</p>2312312<p>23231</p>";
        System.out.println(richTextPattern.matcher(text).find());
    }
}
