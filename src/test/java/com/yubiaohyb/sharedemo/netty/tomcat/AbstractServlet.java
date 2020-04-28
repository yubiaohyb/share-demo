package com.yubiaohyb.sharedemo.netty.tomcat;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/28 17:14
 */
public abstract class AbstractServlet {

    protected abstract void doGet(Request request, Response response) throws Exception;

    protected abstract void doPost(Request request, Response response) throws Exception;
}
