package com.yubiaohyb.sharedemo.thread;

public abstract class ElegantThread extends Thread {
    @Override
    public final void run() {
        try {
            runElegantly();
        } catch (Exception e) {
            handleException(e);
        }
    }

    protected abstract void runElegantly() throws Exception;

    protected abstract void handleException(Exception e);
}
