package com.yubiaohyb.sharedemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  优雅线程测试类
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
public final class TestAbstractElegantThread extends AbstractElegantThread {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestAbstractElegantThread.class);

    @Override
    protected void runElegantly() {
        throw new RuntimeException("123");
    }

    @Override
    protected void handleException(Exception e) {
        LOGGER.error("test success, content:{}", e.getMessage());
    }
}
