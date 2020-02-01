package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:21
 */
public class Chicken implements Bird {

    @Override
    public void fly() {
        System.out.println("鸡儿噗呲噗呲起来了");
    }
}
