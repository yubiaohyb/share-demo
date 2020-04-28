package com.yubiaohyb.sharedemo.netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/13 21:19
 */
@Slf4j
public class ClientBootStrapTest {

    private static void test() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .handler(new ClientChannelInitializer(bootstrap));
        ChannelFuture channelFuture = bootstrap.connect("localhost", 8888).sync();
    }

    public static void main(String[] args) throws Exception {
        test();
    }

}
