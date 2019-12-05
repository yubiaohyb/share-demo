package com.yubiaohyb.sharedemo.algorithm.sort;

/**
 * description  -  冒泡排序
 *
 * 核心思想：循环依次将较小（或较大）值移动至一端
 *
 * 空间复杂度：O(n)
 * 时间复杂度（平均）：O(n^2)
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
