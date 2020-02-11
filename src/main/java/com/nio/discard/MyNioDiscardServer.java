package com.nio.discard;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhangyaping on 20/2/10.
 */
public class MyNioDiscardServer {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(19900));

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            System.out.println("有新的监听事件在channel中发生");

            //选择键，可以理解为被选中了的IO事件。
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                //接收新连接的事件
                if (key.isAcceptable()) {
                    System.out.println("新连接事件");
                    //获取channel
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    System.out.println("有可读数据事件");
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    int length = buffer.position();
                    System.out.println(new String(buffer.array(), 0, length));
                    buffer.clear();
                    channel.close();
                }
                //删除已经处理的事件  避免被循环处理
                it.remove();
            }
        }

        serverSocketChannel.close();
    }

}
