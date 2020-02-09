package com.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by zhangyaping on 20/2/9.
 */
public class EchoHandler implements Runnable {

    SocketChannel channel;
    SelectionKey selectionKey;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    static final int RECIEVING = 0;
    static final int SENDING = 0;

    int state = RECIEVING;

    public EchoHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        channel = socketChannel;
        channel.configureBlocking(false);

        selectionKey = channel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);

        selector.wakeup();
    }

    @Override
    public void run() {
        try {

            if (state == SENDING) {
                channel.write(byteBuffer);
                byteBuffer.clear();
                selectionKey.interestOps(SelectionKey.OP_READ);
                state = RECIEVING;
            } else if (state == RECIEVING) {
                int length = 0;
                while ((length = channel.read(byteBuffer)) > 0) {
                    System.out.println(new String(byteBuffer.array(), 0, length));

                }
                byteBuffer.flip();
                selectionKey.interestOps(SelectionKey.OP_WRITE);

                state = SENDING;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
