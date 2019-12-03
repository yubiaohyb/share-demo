package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  归并排序
 *
 * 核心思想：两个数组两个游标，依次比较后，将值置入新的数组中，需要注意循环结束后的剩余元素
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 14:37
 */
public final class MergeSort {

    public static double[] sort(double[] arr1, double[] arr2) {
        double[] mgdArr = new double[arr1.length+arr2.length];
        int index1=0, index2=0, mgdIndex=0;
        while(index1 <= arr1.length-1 && index2 <= arr2.length-1) {
            if (arr1[index1] < arr2[index2]) {
                mgdArr[mgdIndex] = arr1[index1];
                index1++;
                mgdIndex++;
            } else {
                mgdArr[mgdIndex] = arr2[index2];
                index2++;
                mgdIndex++;
            }
        }
        if (index1 < arr1.length) {
            for (int i=index1; i<=arr1.length-1; i++) {
                mgdArr[mgdIndex++] = arr1[i];
            }
        } else {
            for (int i=index2; i<=arr2.length-1; i++) {
                mgdArr[mgdIndex++] = arr2[i];
            }
        }
        return mgdArr;
    }
}
