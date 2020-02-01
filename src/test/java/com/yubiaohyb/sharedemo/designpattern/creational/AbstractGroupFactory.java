package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  抽象工厂类
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:26
 */
public abstract class AbstractGroupFactory {
    abstract Vehicle getVehilcle();
    abstract Bird getBird();
}
