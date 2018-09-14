package com.yubiaohyb.sharedemo.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;

import java.util.*;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  列名映射索引工具
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:31
 */
public final class ColumnName2IndexHelper {

    /**
     * 获取列名索引映射
     *
     * @param row
     * @return
     */
    public final ColumnName2IndexMapper getColumnName2IndexMapper(HSSFRow row) {
        Map<String, List<Integer>> columnName2IndexMap = new HashMap<>();
        Iterator<Cell> iterator = row.iterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            String contents = cell.getStringCellValue();
            if (StringUtils.isNotBlank(contents)) {
                int columnIndex = cell.getColumnIndex();
                if (columnName2IndexMap.containsKey(contents)) {
                    columnName2IndexMap.get(contents).add(columnIndex);
                } else {
                    List<Integer> columnIndexes = new ArrayList<>();
                    columnIndexes.add(columnIndex);
                    columnName2IndexMap.put(contents, columnIndexes);
                }
            }
        }
        return new ColumnName2IndexMapper(columnName2IndexMap);
    }

    public final class ColumnName2IndexMapper {

        private Map<String, List<Integer>> columnName2IndexMap;

        public ColumnName2IndexMapper(Map<String, List<Integer>> columnName2IndexMap) {
            this.columnName2IndexMap = columnName2IndexMap;
        }

        /**
         * 按列名获取索引
         *
         * @param columnName
         * @return
         */
        public int getColumnIndexByName(String columnName) {
            //Assert.verify(columnName2IndexMap.containsKey(columnName), String.format("未知的列名：%s", columnName));
            if (columnName2IndexMap.containsKey(columnName)) {
                return getColumnIndexByName(columnName, 0);
            }
            return -1;
        }

        /**
         * 按列名获取索引
         *
         * @param columnName
         * @param serialNumber
         * @return
         */
        public int getColumnIndexByName(String columnName, int serialNumber) {
            return columnName2IndexMap.get(columnName).get(serialNumber);
        }
    }


}
