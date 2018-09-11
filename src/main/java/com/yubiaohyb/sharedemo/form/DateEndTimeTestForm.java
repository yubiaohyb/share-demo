package com.yubiaohyb.sharedemo.form;

import com.yubiaohyb.sharedemo.annotation.DateEndTime;
import com.yubiaohyb.sharedemo.annotation.NotNullLabel;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class DateEndTimeTestForm {

    @DateEndTime
    private Date date;

    @NotNullLabel(label = "文本", message = "请先填写文本！")
    private String text;

    @NotNull(message = "请先填写文本2！")
    private String text2;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
