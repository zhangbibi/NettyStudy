package com.nio.discard;

import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhangyaping on 20/2/10.
 */
public class MyNioDiscardClient {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 19900));

        channel.register(selector, SelectionKey.OP_CONNECT);

        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();

                System.out.println(key.interestOps());
                if (key.isConnectable()) {
                    System.out.println(key.interestOps());
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put("Hello".getBytes());

                    channel.finishConnect();

                    channel.write(buffer);

                    buffer.clear();
                }

            }

        }


    }
}
