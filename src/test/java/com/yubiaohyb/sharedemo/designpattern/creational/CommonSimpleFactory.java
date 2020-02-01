package com.yubiaohyb.sharedemo.designpattern.creational;

/**
 * description  -  普通简单工厂
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:05
 */
public class CommonSimpleFactory {
    Vehicle getVehicle(String vehicleType) {
        if ("car".equals(vehicleType)) {
            return new Car();
        } else {
            return new Ship();
        }
    }
}
