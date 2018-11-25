package com.netty.study.sixth.protobuf.mutilType.nettypb;

import com.netty.study.sixth.protobuf.DataInfo;
import com.netty.study.sixth.protobuf.MyDataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhangyaping on 18/11/24.
 */
public class PbHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

//        DataInfo.Student student2 = DataInfo.Student.parseFrom(msg);


        if (msg.getDataType() == MyDataInfo.MyMessage.DataType.CatType) {
            MyDataInfo.Cat cat = msg.getCat();
            System.out.println(cat.getName());
        }

        if (msg.getDataType() == MyDataInfo.MyMessage.DataType.DogType) {
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println(dog.getName());
        }

        if (msg.getDataType() == MyDataInfo.MyMessage.DataType.PersonType) {
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName());
        }

    }
}
