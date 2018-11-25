package com.netty.study.sixth.protobuf.nettypb;

import com.netty.study.sixth.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhangyaping on 18/11/24.
 */
public class PbHandler extends SimpleChannelInboundHandler<DataInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {

//        DataInfo.Student student2 = DataInfo.Student.parseFrom(msg);

        System.out.println(msg.getName());
        DataInfo.Student student2 = DataInfo.Student.newBuilder()
                .setName("woshi").setAge(222).setAddress("fsd").build();

        ctx.writeAndFlush(student2);

    }
}
