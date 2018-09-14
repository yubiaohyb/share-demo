package com.yubiaohyb.sharedemo.thread;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  优雅线程
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
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
