package com.netty.study.sixth.protobuf.mutilType.nettypb;

import com.netty.study.sixth.protobuf.DataInfo;
import com.netty.study.sixth.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhangyaping on 18/11/24.
 */
public class PbClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        MyDataInfo.Cat cat = MyDataInfo.Cat.newBuilder()
                .setName("HelloKitty").setCity("sz").build();

        MyDataInfo.MyMessage message = MyDataInfo.MyMessage.newBuilder()
                .setDataType(MyDataInfo.MyMessage.DataType.CatType).setCat(cat).build();
        ctx.writeAndFlush(message);



    }
}
