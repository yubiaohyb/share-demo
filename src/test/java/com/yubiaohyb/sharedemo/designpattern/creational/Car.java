package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 17:44
 */
public class Car implements Vehicle {

    @Override
    public void drive() {
        System.out.println("汽车跑起来了");
    }
}
