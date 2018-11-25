package com.netty.study.sixth.protobuf.nettypb;

import com.netty.study.sixth.protobuf.DataInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhangyaping on 18/11/24.
 */
public class PbClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("HelloKitty2222").setAge(12).setAddress("xxxx").build();
        ctx.writeAndFlush(student);

        System.out.println(msg.getName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("HelloKitty").setAge(12).setAddress("xxxx").build();
        ctx.writeAndFlush(student);

    }
}
