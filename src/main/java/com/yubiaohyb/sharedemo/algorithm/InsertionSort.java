package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  插入排序
 *
 * 核心思想：默认初始集合有序，循环扩展下标，纳入新元素，再与前面有序元素比较决定排序
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 12:38
 */
public final class InsertionSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        for (int i=0; i<=arr.length-1; i++) {
            for (int j=i; j>0 && arr[j-1]>arr[j]; j--) {
                swap(arr, j, j-1);
            }
        }
    }
}
