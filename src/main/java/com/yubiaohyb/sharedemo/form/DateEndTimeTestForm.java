package com.yubiaohyb.sharedemo.form;

import com.yubiaohyb.sharedemo.annotation.DateEndTime;
import com.yubiaohyb.sharedemo.annotation.NotNullLabel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  测试截止日期时间表单
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
@Data
public class DateEndTimeTestForm {

    @DateEndTime
    private Date endDate;

    @NotNullLabel(label = "文本", message = "请先填写文本！")
    private String text;

    @NotNull(message = "请先填写文本2！")
    private String text2;

}
