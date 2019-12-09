package com.yubiaohyb.sharedemo.regex;

import java.util.Arrays;
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
        String s = "This is the test of they regex ego";
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
//        System.out.println(RegexUtils.replaceAll(s, "\\w+", "<br>$1</br>"));
        System.out.println(RegexUtils.replaceAll(s, "(\\w+)", "<br>$1</br>"));
        //如果不分组则无法进行替换
    }

    @Test
    public void getGroupList() {
        //分组测试
        String s = "This is";
        //注意观察下面，比较差异
        System.out.println(RegexUtils.getGroupList(s, "(\\w{4}) (\\w{2})"));
        System.out.println(RegexUtils.getGroupList(s, "(\\w+) (\\w+)"));
        System.out.println(RegexUtils.getGroupList(s, "(\\w+)"));

        String v = "24:15";
        System.out.println(RegexUtils.getGroupList(v, "(\\d){2}:(\\d{1})"));
        System.out.println(RegexUtils.getGroupList(v, "(\\d){2}:(\\d{2})"));
    }

    @Test
    public void greedyMatch() {
        //贪婪匹配测试
        String s = "123000";
        //注意观察下面，比较差异
        System.out.println(RegexUtils.getGroupList(s, "(\\d+)(0?)"));
        System.out.println(RegexUtils.getGroupList(s, "(\\d+)(0*)"));
        System.out.println(RegexUtils.getGroupList(s, "(\\d+)(0+)"));
        System.out.println(RegexUtils.getGroupList(s, "(\\d+?)(0+)"));

        //比较前后问号差异
        String v = "9999";
        System.out.println(RegexUtils.getGroupList(v, "(9?)(9+)"));
        System.out.println(RegexUtils.getGroupList(v, "(9??)(9+)"));

    }
}
