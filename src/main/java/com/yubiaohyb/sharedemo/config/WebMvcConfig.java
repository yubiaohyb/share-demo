package com.yubiaohyb.sharedemo.config;

import com.yubiaohyb.sharedemo.format.DateEndTimeAnnotationFormatterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.yubiaohyb.sharedemo.controller", "com.yubiaohyb.sharedemo.format"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private DateEndTimeAnnotationFormatterFactory dateEndTimeAnnotationFormatterFactory;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(dateEndTimeAnnotationFormatterFactory);
        super.addFormatters(registry);
    }

}
