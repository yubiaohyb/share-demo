package com.yubiaohyb.sharedemo.netty.heartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/17 16:31
 */
@Slf4j
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        channel.writeAndFlush(channel.remoteAddress() + ": " + msg + "\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("处理客户端消息发生异常", cause);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            super.userEventTriggered(ctx, evt);
        }
        IdleState idleState = ((IdleStateEvent)evt).state();
        if (idleState == IdleState.READER_IDLE) {
            log.info("读连接超时，即将断开");
            ctx.close();
//            ctx.disconnect();
            // close与disconnect有何区别？
            return ;
        }
        super.userEventTriggered(ctx, evt);
    }
}
