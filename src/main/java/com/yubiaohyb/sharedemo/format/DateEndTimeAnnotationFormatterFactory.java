package com.yubiaohyb.sharedemo.format;

import com.yubiaohyb.sharedemo.annotation.DateEndTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  截止日期时间注解格式器工厂
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
@Component
public class DateEndTimeAnnotationFormatterFactory implements AnnotationFormatterFactory<DateEndTime> {

    @Autowired
    private DateEndTimeFormatter dateEndTimeFormatter;

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<>();
        fieldTypes.add(Date.class);
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(DateEndTime annotation, Class<?> fieldType) {
        return this.dateEndTimeFormatter;
    }

    @Override
    public Parser<?> getParser(DateEndTime annotation, Class<?> fieldType) {
        return this.dateEndTimeFormatter;
    }

}
