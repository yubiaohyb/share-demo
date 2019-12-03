package com.yubiaohyb.sharedemo.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 15:17
 */
public class MergeSortTest {

    public static void main(String[] args) {
        double[] arr1 = new double[]{1.0, 3.0, 4.0, 7.0};
        double[] arr2 = new double[]{0.5, 2.0, 5.0, 6.0, 11.0};
        System.out.println(JSON.toJSONString(arr1));
        System.out.println(JSON.toJSONString(arr2));
        System.out.println(JSON.toJSONString(MergeSort.sort(arr1, arr2)));
    }

}
