package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  冒泡排序
 *
 * 核心思想：循环依次将较小（或较大）值移动至一端
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 13:02
 */
public final class BubbleSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        for (int i=0; i<=arr.length-2; i++) {
            for (int j=arr.length-1; j>i; j--) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j);
                }
            }
        }
    }
}
