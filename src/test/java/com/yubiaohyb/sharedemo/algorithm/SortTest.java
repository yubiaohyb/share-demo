package com.yubiaohyb.sharedemo.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 11:35
 */
public class SortTest {

    public static void main(String[] args) {
//        Sort sort = new SelectionSort();
//        Sort sort = new InsertionSort();
//        Sort sort = new BubbleSort();
        Sort sort = new QuickSort();
        double[] arr = new double[]{11.0, 3.0, 22.0, 44.0, 1.0, 13.0};
        System.out.println(JSON.toJSONString(arr));
        sort.sort(arr);
        System.out.println(JSON.toJSONString(arr));
    }

}
