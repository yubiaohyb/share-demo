package com.yubiaohyb.sharedemo.form;

import com.yubiaohyb.sharedemo.annotation.DateEndTime;
import com.yubiaohyb.sharedemo.annotation.NotNullLabel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DateEndTimeTestForm {

    @DateEndTime
    private Date endDate;

    @NotNullLabel(label = "文本", message = "请先填写文本！")
    private String text;

    @NotNull(message = "请先填写文本2！")
    private String text2;

}
