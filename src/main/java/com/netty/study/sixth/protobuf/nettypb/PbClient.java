package com.netty.study.sixth.protobuf.nettypb;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by zhangyaping on 18/11/24.
 */
public class PbClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new PbClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            loopGroup.shutdownGracefully();
        }
    }
}
