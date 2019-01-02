package com.netty.study.first.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;


import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by zhangyaping on 18/11/17.
 */
public class TestServer {

    public static void main(String[] args) throws InterruptedException {

        //事件循环组  boss用于获取连接  worker用于处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //ServerBootstrap是一个辅助类 用于简化地启动一个服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //装配属性 handler()方法针对bossGroup的处理器 childHandler()方法是针对workerGroup的处理器
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
