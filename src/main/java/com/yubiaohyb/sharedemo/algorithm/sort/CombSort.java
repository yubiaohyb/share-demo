package com.yubiaohyb.sharedemo.algorithm.sort;

/**
 * description  -  梳排序
 *
 * 核心思想：先借助步长实现分组排序，再使用优化版冒泡排序完成最终排序
 *
 * 空间复杂度：O(n)
 * 时间复杂度（平均）：O(n^2)
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 19:35
 */
public class CombSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        int step = arr.length;
        while ((step /= 1.3) > 1) {
            for (int i=arr.length-1; i>= step; i--) {
                if (arr[i-step] > arr[i]) {
                    swap(arr, i-step, i);
                }
            }
        }

        SortUtils.optimizedBubbleSort(arr);
    }


}
