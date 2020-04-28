package com.yubiaohyb.sharedemo.netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import java.lang.ref.Reference;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/13 21:21
 */
@Slf4j
public class ClientChannelHandler extends ChannelInboundHandlerAdapter {
    private ScheduledFuture<?> scheduledFuture;
    private GenericFutureListener listener;
    private Bootstrap bootstrap;

    public ClientChannelHandler(Bootstrap bootstrap) {
        super();
        this.bootstrap = bootstrap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("客户端已上线");
        sendHeartBeat(ctx.channel());
    }

    private void sendHeartBeat(Channel channel) {
        int interval = RandomUtils.nextInt(1, 8);
        log.info(interval + "秒后向服务器发送心跳");
        scheduledFuture = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                log.info("向服务器发送心跳");
                channel.writeAndFlush("PING");
            } else {
                log.info("与服务器间连接断开");
//                scheduledFuture.removeListener(listener);
            }
        }, interval, TimeUnit.SECONDS);
        listener = (future) -> sendHeartBeat(channel);
        scheduledFuture.addListener(listener);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("接收消息发生异常", cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        scheduledFuture.removeListener(listener);
        log.info("尝试重连服务器...");
        bootstrap.connect("localhost",8888).sync();
    }

}
