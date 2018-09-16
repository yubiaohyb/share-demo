package com.yubiaohyb.sharedemo.excel;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private ColumnName2IndexHelper() {
    }

    public final static ColumnName2IndexMapper getColumnName2IndexMapper(HSSFRow row) {
        return ColumnName2IndexHelperFactory.getHelper().getName2IndexMapper(row);
    }

    /**
     * 获取列名索引映射
     *
     * @param row
     * @return
     */
    public final ColumnName2IndexMapper getName2IndexMapper(HSSFRow row) {
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

    /**
     * 列名映射索引辅助器工厂类
     * <p>
     * 根据责任单一原则剥离对象的创建
     */
    private static final class ColumnName2IndexHelperFactory {

        public static ColumnName2IndexHelper getHelper() {
            return new ColumnName2IndexHelper();
        }
    }

    public final class ColumnName2IndexMapper {

        private Logger logger = LoggerFactory.getLogger(ColumnName2IndexMapper.class);

        private Map<String, List<Integer>> columnName2IndexMap;

        public ColumnName2IndexMapper(Map<String, List<Integer>> columnName2IndexMap) {
            this.columnName2IndexMap = columnName2IndexMap;
            this.logger.debug("ColumnName2IndexMapper={}", JSON.toJSONString(this.columnName2IndexMap));
        }

        /**
         * 按列名获取索引
         *
         * @param columnName
         * @return
         */
        public int tryGetColumnIndexByName(String columnName) {
            return tryGetColumnIndexByName(columnName, 0);
        }

        /**
         * 按列名获取索引
         *
         * @param columnName
         * @return
         */
        public int tryGetColumnIndexByName(String columnName, int indexInList) {
            if (isColumnExist(columnName, indexInList)) {
                return getColumnIndexByName(columnName, indexInList);
            }
            /**
             * 当获取列不存在时返回-1，将后续处理交由调用方决定
             * 尽量不影响设计逻辑的走通
             */
            return -1;
        }

        /**
         * 按列名获取索引
         *
         * @param columnName
         * @param indexInList
         * @return
         */
        private int getColumnIndexByName(String columnName, int indexInList) {
            return columnName2IndexMap.get(columnName).get(indexInList);
        }

        private boolean isColumnExist(String columnName, int indexInList) {
            return isColumnNameExist(columnName) && isColumnIndexExist(columnName, indexInList);
        }

        private boolean isColumnNameExist(String columnName) {
            return columnName2IndexMap.containsKey(columnName);
        }

        private boolean isColumnIndexExist(String columnName, int indexInList) {
            int size = columnName2IndexMap.get(columnName).size();
            return size > indexInList && indexInList >= 0;
        }
    }


}
