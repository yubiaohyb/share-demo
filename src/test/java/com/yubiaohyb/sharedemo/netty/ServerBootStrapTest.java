package com.yubiaohyb.sharedemo.netty;

import io.netty.channel.ChannelFuture;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/13 17:04
 */
@Slf4j
public class ServerBootStrapTest {
    public class MyChannelInitializer extends ChannelInitializer {

        private void initWebSocketChannel(ChannelPipeline pipeline) {
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpObjectAggregator(4096));
            pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));

            pipeline.addLast(new ServerChannelHandler());
        }

        @Override
        protected void initChannel(Channel channel) {
            ChannelPipeline pipeline = channel.pipeline();
            //initWebSocketChannel(pipeline);
        }
    }

    @Test
    public void test() {

        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChatRoomChannelInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            log.info("netty监听已启动");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("netty监听异常", e);
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
