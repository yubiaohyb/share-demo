package com.yubiaohyb.sharedemo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/13 21:19
 */
@Slf4j
public class ClientBootStrapTest {


    @Test
    public void test() throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelHandler());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8888).sync();
            InputStreamReader inputStreamReader = new InputStreamReader(System.in, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            channelFuture.channel().writeAndFlush(bufferedReader.readLine() + "\r\n");

            channelFuture.channel().closeFuture().sync();
        } finally {
//            group.shutdownGracefully();
        }

    }


}
