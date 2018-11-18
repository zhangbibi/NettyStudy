package com.netty.study.first;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by zhangyaping on 18/11/18.
 */
public class Mt {

    public static void main(String[] args) {
        System.out.println("eh");
        EventLoopGroup workerGroup = new NioEventLoopGroup();
    }
}
