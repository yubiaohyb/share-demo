package com.yubiaohyb.sharedemo.designpattern.creational;


/**
 * description  -  饿汉单例
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 21:06
 */
public class HungerSingleton {

    private static HungerSingleton singleton = new HungerSingleton();

    private HungerSingleton() {}

    public static HungerSingleton getInstance() {
        return singleton;
    }
}
