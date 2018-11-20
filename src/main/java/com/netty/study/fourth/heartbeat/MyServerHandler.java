package com.netty.study.fourth.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by zhangyaping on 18/11/19.
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;

            switch (event.state()) {
                case READER_IDLE:
                    eventType = "read idle";
                    break;
                case WRITER_IDLE:
                    eventType = "write idle";
                    break;

                case ALL_IDLE:
                    eventType = "all idle";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "timeout event: " + eventType);

            ctx.channel().close();
        }
    }
}
