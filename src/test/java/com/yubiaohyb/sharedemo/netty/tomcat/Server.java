package com.yubiaohyb.sharedemo.netty.tomcat;


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2020/4/28 22:25
 */
@Slf4j
public class Server {
    private String basePackage;
    private Map<String, String> name2ClazzPathMap = new HashMap<>();
    private Map<String, Object> name2InstanceMap = new HashMap<>();

    public Server(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() throws Exception {
        cacheBasePackage(basePackage);
        log.info("{}", name2ClazzPathMap);
        runServer();
    }

    private void runServer() throws Exception {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ServerBootstrap serverBootstrap = bootstrap.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ServerChannelInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8888).sync();
            log.info("服务器启动");
            channelFuture.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }

    private void cacheBasePackage(String basePackage) {
        URL resource = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        if (null == resource) {
            log.info("指定包路径不存在");
            return ;
        }
        File directory = new File(resource.getFile());
        Arrays.asList(directory.listFiles()).forEach(file -> {
            if (file.isDirectory()) {
                cacheBasePackage(basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String simpleName = file.getName().replace(".class", "");
                name2ClazzPathMap.put(simpleName.toLowerCase(), basePackage + "." + simpleName);
            }
        });
    }

    public static void main(String[] args) throws Exception {
//        Server server = new Server("com.aa.cc");
        Server server = new Server("com.yubiaohyb.sharedemo.netty.tomcat");
        server.start();
    }
}
