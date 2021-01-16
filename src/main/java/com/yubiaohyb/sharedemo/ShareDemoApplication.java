package com.yubiaohyb.sharedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  共享demo应用入口
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
@SpringBootApplication
@EnableConfigurationProperties
public class ShareDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareDemoApplication.class, args);

    }
}
