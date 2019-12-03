package com.yubiaohyb.sharedemo.algorithm;

import com.alibaba.fastjson.JSON;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 10:52
 */
public class SwapTest {

    public static void main(String[] args) {
        AbstractSort swap = new BubbleSort();
        double[] arr = new double[]{11, 22};
        System.out.println(arr);
        System.out.println(JSON.toJSONString(arr));
        swap.swap(arr, 0, 1);
        System.out.println(arr);
        System.out.println(JSON.toJSONString(arr));
    }

}
