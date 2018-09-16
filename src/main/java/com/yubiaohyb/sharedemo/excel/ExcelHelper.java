package com.yubiaohyb.sharedemo.excel;

import com.alibaba.fastjson.JSON;
import com.yubiaohyb.sharedemo.annotation.ExcelColumn;
import com.yubiaohyb.sharedemo.utils.GenericsUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/16 下午12:38
 */
public abstract class ExcelHelper<T> {

    private Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

    private ColumnName2IndexHelper.ColumnName2IndexMapper columnName2IndexMapper;

    private Map<Field, Integer> field2ColumnIndexMap;

    private boolean titlesSetted = false;

    private static void setTitles(List<String> titles, HSSFRow row) {
        for (int i = 0; i < titles.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
        }
    }

    public void init(List<String> titles, HSSFRow row) {
        setTitles(titles, row);
        columnName2IndexMapper = ColumnName2IndexHelper.getColumnName2IndexMapper(row);
        List<Field> excelColumnFields = getExcelColumnFields();
        this.field2ColumnIndexMap = getField2ColumnIndexMap(columnName2IndexMapper, excelColumnFields);
        this.titlesSetted = true;
    }

    private Map<Field, Integer> getField2ColumnIndexMap(ColumnName2IndexHelper.ColumnName2IndexMapper columnName2IndexMapper, List<Field> excelColumnFields) {
        Map<Field, Integer> field2ColumnIndexMap = new HashMap<>();
        for (Field field : excelColumnFields) {
            int columnIndex = getColumnIndex(columnName2IndexMapper, field);
            field2ColumnIndexMap.put(field, columnIndex);
        }
        return field2ColumnIndexMap;
    }

    private int getColumnIndex(ColumnName2IndexHelper.ColumnName2IndexMapper columnName2IndexMapper, Field field) {
        ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
        String columnName = excelColumn.name();
        int sameNameIndex = excelColumn.sameNameIndex();
        int columnIndex = columnName2IndexMapper.tryGetColumnIndexByName(columnName, sameNameIndex);
        this.logger.debug("ExcelColumn(name = {}, sameNameIndex = {}), Field(name={}, columnIndex={}, class={})", columnName, sameNameIndex, field.getName(), columnIndex, field.getType());
        return columnIndex;
    }

    private List<Field> getExcelColumnFields() {
        Field[] fields = GenericsUtils.getSuperClassGenricType(getClass()).getDeclaredFields();
        List<Field> excelColumnFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                excelColumnFields.add(field);
            }
        }
        return excelColumnFields;
    }

    public void setDataRows(List<T> datas, HSSFSheet sheet, int startRow) {
        HSSFRow row;
        if (!this.titlesSetted) {
            this.logger.error("尚未初始化");
            return;
        }
        for (int i = 0; i < datas.size(); i++) {
            row = sheet.createRow(i + startRow);
            T obj = datas.get(i);
            this.logger.debug("json={}", JSON.toJSONString(obj));
            Iterator<Map.Entry<Field, Integer>> iterator = field2ColumnIndexMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Field, Integer> entry = iterator.next();
                HSSFCell cell = row.createCell(entry.getValue());
                try {
                    Field field = entry.getKey();
                    field.setAccessible(true);
                    cell.setCellValue(field.get(obj).toString());
                } catch (IllegalAccessException e) {
                    this.logger.error("设置行时发生异常", e);
                }
            }
        }
    }

    /**
     * 提供出口方便进行一些自定义处理
     *
     * @return
     */
    protected ColumnName2IndexHelper.ColumnName2IndexMapper getColumnName2IndexMapper() {
        return this.columnName2IndexMapper;
    }

}
