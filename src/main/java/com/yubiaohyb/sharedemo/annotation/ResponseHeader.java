package com.yubiaohyb.sharedemo.annotation;

import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  响应头注解
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/21 10:43
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface ResponseHeader {

    String mediaTypeValue() default MediaType.APPLICATION_OCTET_STREAM_VALUE;

    String fileName();

}
