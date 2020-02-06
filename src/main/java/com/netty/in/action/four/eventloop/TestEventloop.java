package com.netty.in.action.four.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

/**
 * Created by zhangyaping on 20/2/5.
 */
public class TestEventloop {

    public static void main(String[] args) {




        // 3个线程(eventLoop)的group
        EventLoopGroup group = new NioEventLoopGroup(3);

        Channel c = new NioSocketChannel();

        group.register(c);

        c.writeAndFlush(Unpooled.copiedBuffer("Hello", Charset.defaultCharset()));
        ByteBufAllocator allocator = c.alloc();
        ByteBuf b = allocator.buffer(100);


        System.out.println(c.isActive());


//        EventLoop
//        group.next().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("thread in eventloop");
//            }
//        });
//
//        System.out.println("thread main");
    }
}
