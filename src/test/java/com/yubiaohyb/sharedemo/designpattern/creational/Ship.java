package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 17:45
 */
public class Ship implements Vehicle {

    @Override
    public void drive() {
        System.out.println("小船游起来了");
    }
}
