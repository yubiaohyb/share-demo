package com.yubiaohyb.sharedemo.netty.basic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2022/4/11 11:49 PM
 */
@Slf4j
public class ClientDemoHandler1 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress().toString() + "远程服务器链接成功");
        System.out.println(ctx.channel().remoteAddress().toString() + "远程服务器链接成功");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info((String)msg);
        System.out.println(Objects.isNull(msg));
        //ctx.fireChannelRead(msg);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("会话已注销");
        ctx.fireChannelUnregistered();
    }
}
