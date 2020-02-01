package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:28
 */
public class Group1Factory extends AbstractGroupFactory {

    @Override
    Vehicle getVehilcle() {
        return new Car();
    }

    @Override
    Bird getBird() {
        return new Chicken();
    }
}
