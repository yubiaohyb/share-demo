package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  functionDescrption
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

        OptimizedBubbleSort optimizedBubbleSort = new OptimizedBubbleSort();
        optimizedBubbleSort.sort(arr);
    }


}
