package com.yubiaohyb.sharedemo.thread;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 *     这里的线程优雅并不是完全的优雅，或者说并不是真正的优雅。
 *     这里只是将线程的异常捕捉处理从原有逻辑中剥离出来，使得编程者更专注于业务。
 *     当涉及到资源释放等还是需要额外添加tryfinally处理，另外代码的执行效率和命名需要编程者自己把握。
 * </p>
 * description  -  优雅线程
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2018/9/15 上午1:02
 */
public abstract class AbstractElegantThread extends Thread {

    @Override
    public final void run() {
        try {
            runElegantly();
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * 运行执行业务逻辑
     *
     * @throws Exception
     */
    protected abstract void runElegantly() throws Exception;

    /**
     * 处理异常
     *
     * @param e
     */
    protected abstract void handleException(Exception e);
}
