package com.yubiaohyb.sharedemo.netty.basic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

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
public class ServerDemoHandler1 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(ctx.channel().remoteAddress().toString() + "连接激活");
        ctx.writeAndFlush("欢迎你" + ctx.channel().remoteAddress().toString());
        ctx.fireChannelActive();
    }
}
