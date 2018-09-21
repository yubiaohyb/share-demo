package com.yubiaohyb.sharedemo.config;

import com.yubiaohyb.sharedemo.format.DateEndTimeAnnotationFormatterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 *     这个版本的spring中已经将WebMvcConfigurer标记为过时，所以替换掉。
 *     但是如果继承WebMvcConfigurationSupport的话会导致spring boot自动配置的失效，
 *     详细参照WebMvcAutoConfiguration。
 * </p>
 *
 * description  -  web mvc配置
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.yubiaohyb.sharedemo.controller", "com.yubiaohyb.sharedemo.format"})
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private DateEndTimeAnnotationFormatterFactory dateEndTimeAnnotationFormatterFactory;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(dateEndTimeAnnotationFormatterFactory);
    }

}
