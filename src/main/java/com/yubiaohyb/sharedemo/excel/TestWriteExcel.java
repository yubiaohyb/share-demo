package com.yubiaohyb.sharedemo.excel;

import com.yubiaohyb.sharedemo.vo.TestExcelVo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.yubiaohyb.sharedemo.excel.TestExcelConstant.*;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/16 上午10:05
 */
public class TestWriteExcel {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestWriteExcel.class);

    public static void main(String[] args) {
        List<String> titles = getTitles();
        List<TestExcelVo> excelVoes = getTestExcelVos();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        TestExcelVoHelper excelHelper = new TestExcelVoHelper();
        excelHelper.init(titles, row);
        int startRow = 1;
        excelHelper.setDataRows(excelVoes, sheet, startRow);
    }


    private static List<TestExcelVo> getTestExcelVos() {
        List<TestExcelVo> excelVoes = new ArrayList<>();
        TestExcelVo vo = new TestExcelVo();
        vo.setSequenceNo("1");
        vo.setName("小冰");
        vo.setNickname("小冰冰");
        vo.setAge(18);
        excelVoes.add(vo);
        vo = new TestExcelVo();
        vo.setSequenceNo("2");
        vo.setName("小虎");
        vo.setNickname("小虎虎");
        vo.setAge(18);
        excelVoes.add(vo);
        return excelVoes;
    }

    private static List<String> getTitles() {
        List<String> titles = new ArrayList<>();
        titles.add(SEQUENCE_NO);
        titles.add(NAME);
        titles.add(AGE);
        titles.add(NAME);
        return titles;
    }


}
