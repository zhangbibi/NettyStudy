package com.netty.in.action.first.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by zhangyaping on 20/2/4.
 */
public class EchoServer {

    public static void main(String[] args) throws InterruptedException {
        EchoServerHandler echoServerHandler = new EchoServerHandler();

        EventLoopGroup group = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(9900))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(echoServerHandler);
                        }
                    });

            ChannelFuture f = bootstrap.bind().sync();
            f.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }


    }
}
