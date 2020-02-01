package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:31
 */
public class Goup2Factory extends AbstractGroupFactory {

    @Override
    Vehicle getVehilcle() {
        return new Ship();
    }

    @Override
    Bird getBird() {
        return new Eagle();
    }
}
