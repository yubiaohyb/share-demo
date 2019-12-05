package com.yubiaohyb.sharedemo.algorithm.sort;

/**
 * description  -  计数排序（非比较排序，特殊的桶排序）
 *
 * 核心思想：获取数组的上下边界，构建辅助数组，利用其下标统计源数据组，并完成排序
 *
 * 空间复杂度：不确定，可能远大于O(n)，且记为O(N)
 * 时间复杂度（平均）：O(N)
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/4 11:16
 */
public final class CountSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        int max = (int)getMax(arr);
        double[] countArr = new double[max+1];
        for (int i=0; i<=arr.length-1; i++) {
            int index = (int)arr[i];
            countArr[index]++;
        }
        for (int j=0,arrIndex = 0; j<=countArr.length-1; j++) {
            while (countArr[j]>0) {
                arr[arrIndex] = j;
                arrIndex++;
                countArr[j]--;
            }
        }
    }

    private double getMax(double[] arr) {
        double max = arr[0];
        for (int i=1; i<arr.length-1; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

}
