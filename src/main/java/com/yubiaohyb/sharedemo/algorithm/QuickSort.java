package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  快速排序
 *
 * 核心思想：在数组的指定下标范围内，以起始下标位置的元素值作为基准值，其他元素在比较后，通过交换位置，分布在基准值的两边
 *
 * 空间复杂度：O(n)
 * 时间复杂度（平均）：O(n*logn)
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 15:40
 */
public class QuickSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        sort(arr, 0, arr.length-1);
    }

    public void sort(double[] arr, int indexI, int indexJ) {
        int markIndex=indexI;
        for (int i=indexI; i<=indexJ; i++) {
            if (i == markIndex) {
                continue;
            }
            if (arr[i] < arr[markIndex]) {
                for (int j=i;j>markIndex; j--) {
                    swap(arr, j, j-1);
                }
                //需要注意游标前移
                markIndex++;
            }
            sort(arr, indexI, markIndex-1);
            sort(arr, markIndex+1, indexJ);
        }
    }

}
