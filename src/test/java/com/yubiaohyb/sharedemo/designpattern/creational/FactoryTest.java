package com.yubiaohyb.sharedemo.designpattern.creational;

import org.junit.Test;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 18:01
 */
public class FactoryTest {

    @Test
    public void testSimpleFactory() {
        //普通简单工厂
        CommonSimpleFactory commonSimpleFactory = new CommonSimpleFactory();
        commonSimpleFactory.getVehicle("car").drive();

        //静态方法工厂
        StaticMethodFactory.getShip().drive();
    }

    @Test
    public void testFactoryPattern() {
        VehicleFactory carFactory = new CarFactory();
        carFactory.getObject().drive();

        VehicleFactory shipFactory = new ShipFactory();
        shipFactory.getObject().drive();
    }

    @Test
    public void testAbstractFactoryPattern() {
        AbstractGroupFactory factory = new Group1Factory();
        factory.getBird().fly();
        factory.getVehilcle().drive();

        factory = new Goup2Factory();
        factory.getBird().fly();
        factory.getVehilcle().drive();
    }
}
