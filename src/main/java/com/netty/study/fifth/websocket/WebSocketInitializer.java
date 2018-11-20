package com.netty.study.fifth.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by zhangyaping on 18/11/20.
 */
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        //对http请求与响应分段聚合的处理器
        pipeline.addLast(new HttpObjectAggregator(8192));
        //websocket传递消息是以frame 帧为单位的
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new WebSocketHandler());
    }
}
