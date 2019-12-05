package com.yubiaohyb.sharedemo.algorithm.sort;

/**
 * description  -  冒泡排序（优化版）
 *
 * 核心思想：在冒泡排序基础上，添加是否已经有序标记，如果已经有序，则跳出后续循环
 *
 * 空间复杂度：O(n)
 * 时间复杂度（平均）：O(n^2)
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 19:14
 */
public class OptimizedBubbleSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        boolean ordered = false;
        for (int i=0; i<arr.length-2 && !ordered; i++) {
            ordered = true;
            for (int j=arr.length-1; j>i; j--) {
                if (arr[j-1] > arr[j]) {
                    swap(arr, j-1, j);
                    ordered = false;
                }
            }
        }
    }
}
