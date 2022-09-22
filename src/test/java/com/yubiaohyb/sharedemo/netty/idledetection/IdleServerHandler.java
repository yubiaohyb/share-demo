package com.yubiaohyb.sharedemo.netty.idledetection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2022/4/21 9:53 AM
 */
@Slf4j
public class IdleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{}会话激活", ctx.channel().remoteAddress().toString());
        ctx.fireChannelActive();
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.fireChannelRead(msg);
//    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            log.info("{}会话空闲：{}， 通道关闭", ctx.channel().remoteAddress().toString(), evt.toString());
            ctx.channel().close();
            return;
        }
        ctx.fireUserEventTriggered(evt);
    }
}
