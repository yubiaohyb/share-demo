package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  堆排序
 *
 * 核心思想：按照最大（或小）堆组织规则填充数据，然后将极值移到一端，再对剩余元素重新排序，往复循环
 *
 * 空间复杂度：O(n)
 * 时间复杂度（平均）：O(n*logn)
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/4 17:25
 */
public class HeapSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        for (int i=arr.length-1; i>=0; i--) {
            sink(arr, arr.length-1, i);
        }
        for (int i=arr.length-1; i>=1; i--) {
            swap(arr, 0, i);
            sink(arr, i-1, 0);
        }
    }

    private void sink(double[] arr, int borderIndex, int parentIndex) {
        int lftSonIndex = 2*parentIndex+1;
        int rhtSonIndex = 2*parentIndex+2;
        int largerSonIndex;

        if (lftSonIndex < borderIndex) {
            largerSonIndex = arr[lftSonIndex] > arr[rhtSonIndex] ? lftSonIndex : rhtSonIndex;
        } else if (lftSonIndex == borderIndex) {
            largerSonIndex = lftSonIndex;
        } else {
            return;
        }
        if (arr[largerSonIndex] > arr[parentIndex]) {
            swap(arr, parentIndex, largerSonIndex);
            sink(arr, borderIndex, largerSonIndex);
        }

    }
}
