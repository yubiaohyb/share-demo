package com.yubiaohyb.sharedemo.netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/13 21:29
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private Bootstrap bootstrap;

    public ClientChannelInitializer(Bootstrap bootstrap) {
        super();
        this.bootstrap = bootstrap;
    }

    @Override
    protected void initChannel(SocketChannel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new ClientChannelHandler(bootstrap));
    }
}
