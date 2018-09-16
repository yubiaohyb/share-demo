package com.yubiaohyb.sharedemo.vo;

import com.yubiaohyb.sharedemo.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

import static com.yubiaohyb.sharedemo.excel.TestExcelConstant.*;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  测试excel实体类
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/16 上午10:01
 */
@Data
public class TestExcelVo implements Serializable {

    @ExcelColumn(name = SEQUENCE_NO)
    private String sequenceNo;

    @ExcelColumn(name = NAME)
    private String name;

    @ExcelColumn(name = NAME, sameNameIndex = 1)
    private String nickname;

    @ExcelColumn(name = AGE)
    private Integer age;
}
