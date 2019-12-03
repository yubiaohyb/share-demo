package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  数组元素间互换
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 10:38
 */
abstract class AbstractSort implements Sort {
    protected void swap(double[] arr, int indexI, int indexJ) {

//        arr[indexI] = arr[indexI] + arr[indexJ];
//        //转换
//        arr[indexJ] = arr[indexI] - arr[indexJ];
//        arr[indexI] = arr[indexI] - arr[indexJ];

        //上面的代码存在一个问题：当 indexI == indexJ，对应元素的值在计算后就会变成0

        arr[indexI] = (arr[indexI] + arr[indexJ])/2;
        //转换
        arr[indexJ] = arr[indexI]*2 - arr[indexJ];
        arr[indexI] = arr[indexI]*2 - arr[indexJ];

        //下面的代码和中间的这段代码体现了两种思想：以空间换时间、以时间换空间

//        double tmp = arr[indexI];
//        arr[indexI] = arr[indexJ];
//        arr[indexJ] = tmp;
    }
}
