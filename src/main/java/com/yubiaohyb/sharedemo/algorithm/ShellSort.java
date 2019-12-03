package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  希尔排序
 *
 * 核心思想：基于插入排序，添加步长因素
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 16:54
 */
public class ShellSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        int step = getStep(arr.length);
        for (;;) {
            for (int i=step; i<=arr.length-1; i++) {
                for (int j=i; j>=step && arr[j-step]>arr[j]; j-=step) {
                    swap(arr, j, j-step);
                }
            }
            if (1 == step) {
                break;
            }
            step = devideStep(step);
        }

    }

    private int devideStep(int step) {
        step /= 3;
        return step;
    }

    private int getStep(int length) {
        int step = 1;
        while (step*3 < length) {
            step *= 3;
        }
        return step;
    }
}
