package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:06
 */
public class StaticMethodFactory {
    static Car getCar() {
        return new Car();
    }

    static Ship getShip() {
        return new Ship();
    }
}
