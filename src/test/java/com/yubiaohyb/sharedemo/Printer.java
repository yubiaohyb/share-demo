package com.yubiaohyb.sharedemo;

import com.alibaba.fastjson.JSON;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/2/1 22:17
 */
public interface Printer {
    default void print(Object object) {
        System.out.println("====================");
        RuntimeException runtimeException = new RuntimeException();
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        System.out.println(stackTrace[1].getClassName() + "#" + stackTrace[1].getMethodName());
        System.out.println(JSON.toJSONString(object));
    }

}
