package com.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhangyaping on 20/2/9.
 * 单线程版本的reactor模式
 */
public class SingleThreadReactor implements Runnable {

    Selector selector;
    ServerSocketChannel serverSocketChannel;

    SingleThreadReactor() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress address =
                new InetSocketAddress("127.0.0.1", 18899);
        serverSocketChannel.socket().bind(address);
        //非阻塞
        serverSocketChannel.configureBlocking(false);

        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new AcceptorHandler());
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selected.iterator();

                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    dispatch(sk);
                }

                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void dispatch(SelectionKey sk) {
        Runnable handler = (Runnable) sk.attachment();
        if (handler != null) {
            handler.run();
        }
    }

    private class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new EchoHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new SingleThreadReactor()).start();
    }
}
