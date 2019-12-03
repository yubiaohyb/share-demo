package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  选择排序
 *
 * 核心思想：循环获取极值下标，依次将元素交换到一端
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 11:21
 */
public final class SelectionSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        int minIndex;
        double min;
        for (int i=0; i<= arr.length-2; i++) {
            min = arr[i];
            minIndex = i;
            for (int j=i+1; j<=arr.length-1; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            if (i != minIndex) {
                swap(arr, i, minIndex);
            }
        }
    }

}
