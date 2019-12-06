package com.yubiaohyb.sharedemo.regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/6 19:25
 */
public class RegexTest {

    @Test
    public void isTelephone() {
        Assert.isTrue(RegexUtils.isTelephone("1213132"), "不是电话号码");
        Assert.isTrue(RegexUtils.isTelephone("12345-232323232"), "不是电话号码");
        Assert.isTrue(RegexUtils.isTelephone("0554-8991173"), "不是电话号码");
    }

    @Test
    public void split1() {
        System.out.println(Arrays.toString(RegexUtils.split1("a b c", " ")));
        System.out.println(Arrays.toString(RegexUtils.split1("a b  c", " ")));
        System.out.println(Arrays.toString(RegexUtils.split1("a b  c", "\\s+")));
        //重点注意下面为什么这么写！！！
        System.out.println(Arrays.toString(RegexUtils.split1("a ,,;b  c", "[\\s\\,\\;]+")));
    }

    @Test
    public void find1() {
        String s = "This is the test of they regex";
        System.out.println(RegexUtils.find(s, "the\\w*"));
    }

    @Test
    public void find2() {
        String s = "This is the test of they regex";
        System.out.println(RegexUtils.find(s, "\\w*e\\w*"));
    }

    @Test
    public void find3() {
        String s = "This is the test of they regex";
        System.out.println(RegexUtils.find(s, "\\w+"));
    }

    @Test
    public void replaceAll() {
        String s = "This is the test of they regex";
        //注意观察下面，有括号和没括号的差异
        System.out.println(RegexUtils.replaceAll(s, "\\w+", "<br>$1</br>"));
        System.out.println(RegexUtils.replaceAll(s, "(\\w+)", "<br>$1</br>"));
    }
}
