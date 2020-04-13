package com.yubiaohyb.sharedemo.netty;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/13 20:20
 */
@Slf4j
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    private static ChannelGroup group = new DefaultChannelGroup("chat room", GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info(ctx.channel().remoteAddress() + ": " + msg);
//        ctx.channel().writeAndFlush("from server: " + UUID.randomUUID());

        group.writeAndFlush(ctx.channel().remoteAddress() + ": " + msg);
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        group.writeAndFlush(ctx.channel().remoteAddress() + "上线");
        group.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        group.writeAndFlush(ctx.channel().remoteAddress() + "下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.info("处理来自客户端信息发生异常", cause);
    }
}
