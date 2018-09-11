package com.yubiaohyb.sharedemo.format;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Component
public class DateEndTimeFormatter implements Formatter<Date> {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String REGEXP = "^\\d{1,4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";

    @Override
    public String print(Date object, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.format(object);
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        String trimText = text.trim();
        if (null == trimText || !trimText.matches(REGEXP)) {
            throw new IllegalArgumentException(String.format("日期处理异常，当前值：%s，期望值格式：%s", trimText, DATE_FORMAT));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(trimText);
        } catch (ParseException e) {
            throw new ParseException(String.format("日期处理异常，当前值：%s，期望值格式：%s", trimText, DATE_FORMAT),
                    e.getErrorOffset());
        }
        long oldTimeInMillis = date.getTime();
        long newTimeInMillis = oldTimeInMillis + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000 + 999;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(newTimeInMillis);
        return calendar.getTime();
    }

}
