package com.future.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;

/**
 * Created by zhangyaping on 20/2/9.
 */
public class TestNettyChannelFutrue {

    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(10888));

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("connect success");
                } else {
                    System.out.println("connect failed");
                }

            }
        });


    }
}
