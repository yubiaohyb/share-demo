package com.yubiaohyb.sharedemo.netty.tomcat.location;

import com.yubiaohyb.sharedemo.netty.tomcat.AbstractServlet;
import com.yubiaohyb.sharedemo.netty.tomcat.Request;
import com.yubiaohyb.sharedemo.netty.tomcat.Response;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/30 16:56
 */
public class DemoServlet extends AbstractServlet {

    @Override
    protected void doGet(Request request, Response response) throws Exception {
        response.write("netty world.\n to be continue.");
    }

    @Override
    protected void doPost(Request request, Response response) throws Exception {
        doGet(request, response);
    }
}
