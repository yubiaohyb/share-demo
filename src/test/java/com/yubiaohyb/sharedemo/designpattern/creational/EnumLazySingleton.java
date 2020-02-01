package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  懒汉单例模式（单元素枚举类）
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 21:24
 */
public enum EnumLazySingleton {
    SINGLETON();

    public EnumLazySingleton getInstance() {
        return SINGLETON;
    }
}
