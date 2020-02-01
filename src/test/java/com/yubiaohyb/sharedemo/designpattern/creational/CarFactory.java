package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:12
 */
public class CarFactory extends VehicleFactory<Car> {

    @Override
    Car getObject() {
        return new Car();
    }
}
