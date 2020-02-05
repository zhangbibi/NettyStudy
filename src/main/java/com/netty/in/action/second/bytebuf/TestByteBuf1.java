package com.netty.in.action.second.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by zhangyaping on 20/2/4.
 */
public class TestByteBuf1 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.directBuffer(10, 100);
//        Unpooled.copiedBuffer("HelloWorld", Charset.defaultCharset());


//        ByteBuf byteBuf = Unpooled.compositeBuffer(100);

        if (byteBuf.hasArray()) {
            System.out.println("hasArray");
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
            int length = byteBuf.readableBytes();

            System.out.println(offset);
            System.out.println(length);

            byte b = byteBuf.readByte();
            System.out.println(b);

            System.out.println(byteBuf.readerIndex());
        } else {
            System.out.println(byteBuf.readableBytes());
            byteBuf.writeCharSequence("HelloKitty", Charset.defaultCharset());
            byte b = byteBuf.readByte();
            System.out.println((char) b);
        }
    }
}
