package com.yubiaohyb.sharedemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestElegantThread extends ElegantThread {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestElegantThread.class);

    @Override
    protected void runElegantly() {
        throw new RuntimeException("123");
    }

    @Override
    protected void handleException(Exception e) {
        LOGGER.error("test success, content:{}", e.getMessage());
    }
}
