package com.yubiaohyb.sharedemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  Excel列注解
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/16 上午9:54
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface ExcelColumn {

    String name();

    int sameNameIndex() default 0;

}
