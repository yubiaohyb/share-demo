package com.yubiaohyb.sharedemo.demo;

import static com.yubiaohyb.sharedemo.demo.ExcelConstant.AGE;
import static com.yubiaohyb.sharedemo.demo.ExcelConstant.NAME;
import static com.yubiaohyb.sharedemo.demo.ExcelConstant.SEQUENCE_NO;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yubiaohyb.sharedemo.excel.ExcelHelper;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/16 上午10:05
 */
public class WriteExcel {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteExcel.class);

    public static HSSFWorkbook getWorkbook()  {
        List<String> titles = getTitles();
        List<ExcelVo> excelVoes = getTestExcelVos();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        ExcelHelper<ExcelVo> excelHelper = new ExcelHelper<>();
        excelHelper.init(titles, row);
        int startRowNo = 1;
        excelHelper.setDataRows(excelVoes, sheet, startRowNo);
        return workbook;
    }

    public static HSSFWorkbook getNewWorkbook()  {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        return workbook;
    }

    private static List<ExcelVo> getTestExcelVos() {
        List<ExcelVo> excelVoes = new ArrayList<>();
        ExcelVo vo = new ExcelVo();
        vo.setSequenceNo("1");
        vo.setName("小冰");
        vo.setNickname("小冰冰");
        vo.setAge(18);
        excelVoes.add(vo);
        vo = new ExcelVo();
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
