package com.yubiaohyb.sharedemo.excel;

import com.alibaba.fastjson.JSON;
import com.yubiaohyb.sharedemo.annotation.ExcelColumn;
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
public class ExcelHelper<T> {

    private Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

    private ColumnName2IndexHelper.ColumnName2IndexMapper columnName2IndexMapper;

    private Map<Field, Integer> field2ColumnIndexMap;

    private boolean initialized = false;

    private static void setTitles(List<String> titles, HSSFRow row) {
        for (int i = 0; i < titles.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(titles.get(i));
        }
    }

    public void init(List<String> titles, HSSFRow row) {
        setTitles(titles, row);
        this.logger.debug("titles={}", JSON.toJSONString(titles));
        this.columnName2IndexMapper = ColumnName2IndexHelper.getColumnName2IndexMapper(row);
        this.initialized = true;
    }

    private Map<Field, Integer> getField2ColumnIndexMap(List<Field> excelColumnFields) {
        Map<Field, Integer> field2ColumnIndexMap = new HashMap<>();
        for (Field field : excelColumnFields) {
            int columnIndex = getColumnIndex(field);
            field2ColumnIndexMap.put(field, columnIndex);
        }
        return field2ColumnIndexMap;
    }

    private int getColumnIndex(Field field) {
        ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
        String columnName = excelColumn.name();
        int sameNameIndex = excelColumn.sameNameIndex();
        int columnIndex = this.columnName2IndexMapper.tryGetColumnIndexByName(columnName, sameNameIndex);
        this.logger.debug("ExcelColumn(name = {}, sameNameIndex = {}) ==> columnIndex={}, Field(name={}, class={})", columnName, sameNameIndex, columnIndex, field.getName(), field.getType());
        return columnIndex;
    }

    private List<Field> getExcelColumnFields(T obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<Field> excelColumnFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                excelColumnFields.add(field);
            }
        }
        return excelColumnFields;
    }

    public void setDataRows(List<T> datas, HSSFSheet sheet, int startRowNo) {
        HSSFRow row;
        if (!this.initialized) {
            this.logger.error("尚未初始化");
            return;
        }
        for (int i = 0; i < datas.size(); i++) {
            row = sheet.createRow(i + startRowNo);
            T obj = datas.get(i);
            this.trySetField2ColumnIndexMap(obj);
            this.logger.debug("json={}", JSON.toJSONString(obj));
            this.setRowCells(row, obj);
        }
    }

    private void setRowCells(HSSFRow row, T obj) {
        Iterator<Map.Entry<Field, Integer>> iterator = this.field2ColumnIndexMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Field, Integer> entry = iterator.next();
            HSSFCell cell = row.createCell(entry.getValue());
            Field field = null;
            try {
                field = entry.getKey();
                field.setAccessible(true);
                cell.setCellValue(field.get(obj).toString());
            } catch (IllegalAccessException e) {
                this.logger.error("设置行表格时时发生异常,fieldName={},obj={},columnIndex={}", field.getName(), JSON.toJSONString(obj), entry.getValue(), e);
            }
        }
    }

    private void trySetField2ColumnIndexMap(T obj) {
        if (null == this.field2ColumnIndexMap) {
            List<Field> excelColumnFields = getExcelColumnFields(obj);
            this.field2ColumnIndexMap = getField2ColumnIndexMap(excelColumnFields);
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
