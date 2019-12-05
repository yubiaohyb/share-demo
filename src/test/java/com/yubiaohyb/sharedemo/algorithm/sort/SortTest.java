package com.yubiaohyb.sharedemo.algorithm.sort;

import com.alibaba.fastjson.JSON;
import com.yubiaohyb.sharedemo.algorithm.sort.SortUtils;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 11:35
 */
public class SortTest {

    public static void main(String[] args) {
        double[] arr = new double[]{11.0, 3.0, 1000.0, 22.0, 300.0, 44.0, 1.0, 13.0, 100.0};
        System.out.println(JSON.toJSONString(arr));
//        SortUtils.bubbleSort(arr);
//        SortUtils.insertionSort(arr);
//        SortUtils.selectionSort(arr);

//        SortUtils.shellSort(arr);
//        SortUtils.combSort(arr);
        SortUtils.heapSort(arr);

//        SortUtils.quickSort(arr);
//        SortUtils.radixSort(arr);
//        SortUtils.countSort(arr);

        System.out.println(JSON.toJSONString(arr));
    }

}
