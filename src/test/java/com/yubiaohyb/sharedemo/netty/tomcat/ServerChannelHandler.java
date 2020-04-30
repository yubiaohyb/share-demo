package com.yubiaohyb.sharedemo.netty.tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/30 15:25
 */
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {

    private final Server server;

    public ServerChannelHandler(Server server) {
        this.server = server;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest)msg;
            Request request = new DefaultRequest(httpRequest);
            String servletName = request.getPath().split("/")[1];
            AbstractServlet servlet = server.getServlet(servletName);
            if ("POST".equals(request.getMethod())){
                servlet.doPost(request, new DefaultResponse(httpRequest, ctx));
            } else {
                servlet.doGet(request, new DefaultResponse(httpRequest, ctx));
            }
        }
    }
}
