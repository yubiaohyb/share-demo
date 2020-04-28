package com.yubiaohyb.sharedemo.netty.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

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
            .handler(new ClientChatRoomChannelInitializer());
        ChannelFuture channelFuture = bootstrap.connect("localhost", 8888).sync();
        log.info("连接到服务器");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        channelFuture.channel().writeAndFlush(bufferedReader.readLine() + "\r\n");
    }

    public static void main(String[] args) throws Exception {
        test();
    }

}
