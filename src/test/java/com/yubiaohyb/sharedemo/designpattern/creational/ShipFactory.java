package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:37
 */
public class ShipFactory extends VehicleFactory {

    @Override
    Vehicle getObject() {
        return new Ship();
    }
}
