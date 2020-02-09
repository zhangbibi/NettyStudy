package com.future.guava;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangyaping on 20/2/9.
 */
public class TestGuavaFuture {

    public static void main(String[] args) {
        ExecutorService jpool = Executors.newFixedThreadPool(3);
        //Guava 线程池
        ListeningExecutorService gpool = MoreExecutors.listeningDecorator(jpool);

        ListenableFuture future = gpool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("异步任务逻辑");
                return "";
            }
        });

        Futures.addCallback(future, new FutureCallback() {
            @Override
            public void onSuccess(@NullableDecl Object result) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failure");
            }
        }, new ex());
    }


    static class ex implements Executor {

        @Override
        public void execute(Runnable command) {
            System.out.println("Executor run ...");
        }
    }
}
