package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  懒汉单例模式（静态内部类）
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 21:19
 */
public class StaticInnerClassLazySingleton {
    private StaticInnerClassLazySingleton() {}

    private static class SingletionHolder {
        private static StaticInnerClassLazySingleton singleton = new StaticInnerClassLazySingleton();
    }

    static StaticInnerClassLazySingleton getInstance() {
        return SingletionHolder.singleton;
    }
}
