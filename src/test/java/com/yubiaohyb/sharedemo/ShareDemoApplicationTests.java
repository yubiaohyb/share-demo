package com.yubiaohyb.sharedemo;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShareDemoApplicationTests {

    @Test
    public void contextLoads() {

    }

    private static Pattern pattern = Pattern.compile("(\\d+\\.)+\\d+");
    public static String getVersion(String appVersionStr) {
        if (StringUtils.isBlank(appVersionStr)) {
            return "1.0.0";
        }
        Matcher matcher = pattern.matcher(appVersionStr);
        if (matcher.find()) {
            return matcher.group();
        }
        return appVersionStr;
    }

    public static boolean before(String currentVersion, String baseVersion) {
        String[] current = getVersion(currentVersion).split("\\.");
        String[] base = getVersion(baseVersion).split("\\.");
        for (int i=0;i<current.length; i++) {
            int compare = Integer.valueOf(current[i]).compareTo(Integer.valueOf(base[i]));
            if (compare != 0) {
                return compare < 0;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(before("2.13.5", "2.13.5"));
    }

}
