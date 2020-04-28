package com.yubiaohyb.sharedemo.netty.tomcat;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/28 21:27
 */
public class DefaultServlet extends AbstractServlet {

    @Override
    protected void doGet(Request request, Response response) throws Exception {
        String servletName = request.getPath().split("/")[1];
        response.write("404 - there is no this servlet: "+ servletName);
    }

    @Override
    protected void doPost(Request request, Response response) throws Exception {
        doGet(request, response);
    }
}
