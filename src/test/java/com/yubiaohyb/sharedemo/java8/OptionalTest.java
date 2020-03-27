package com.yubiaohyb.sharedemo.java8;

import com.yubiaohyb.sharedemo.Printer;
import java.util.Optional;
import lombok.Data;
import org.junit.Test;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/3/27 11:55
 */
public class OptionalTest implements Printer {
    @Data
    class OptionalObject {
        private OptionalObject value;
    }

    @Test
    public void test() {
        OptionalObject obj = null;
        print(Optional.ofNullable(obj)
            .map(OptionalObject::getValue)
            .map(OptionalObject::getValue)
//            .get()  java.util.NoSuchElementException: No value present
            .orElse(new OptionalObject()));
    }

}
