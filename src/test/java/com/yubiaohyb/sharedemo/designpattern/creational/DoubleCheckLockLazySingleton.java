package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  懒汉单例模式（双重检查锁）
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 21:13
 */
public class DoubleCheckLockLazySingleton {
    private static volatile DoubleCheckLockLazySingleton singleton;

    private DoubleCheckLockLazySingleton() {}

    static DoubleCheckLockLazySingleton getInstance() {
        if (null == singleton) {
            synchronized (DoubleCheckLockLazySingleton.class) {
                if (null == singleton) {
                    singleton = new DoubleCheckLockLazySingleton();
                }
            }
        }
        return singleton;
    }
}
