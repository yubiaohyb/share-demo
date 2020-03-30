package com.yubiaohyb.sharedemo.java8;

import com.yubiaohyb.sharedemo.Printer;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/3/30 22:10
 */
@Slf4j
public class StreamTest implements Printer {
    @Test
    public void test() {
        ForkJoinPool customThreadPool = new ForkJoinPool(10);
        int sum = 0;
        try {
            sum = customThreadPool.submit(
                () -> IntStream.range(1, 101).parallel()
                    .sum()).get();
        } catch (Exception e) {
            log.error("并行计算和发生异常", e);
        }
//        for (int i=1; i<= 100; i++) {
//            sum += i;
//        }
        print(sum);
    }

}
