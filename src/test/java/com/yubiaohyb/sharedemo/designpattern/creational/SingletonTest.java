package com.yubiaohyb.sharedemo.designpattern.creational;

import com.yubiaohyb.sharedemo.Printer;
import org.junit.Test;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 22:13
 */
public class SingletonTest implements Printer {

    @Test
    public void testHungerSingleton() {
        print(HungerSingleton.getInstance().equals(HungerSingleton.getInstance()));
    }
}
