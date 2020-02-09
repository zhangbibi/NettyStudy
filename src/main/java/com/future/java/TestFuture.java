package com.future.java;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by zhangyaping on 20/2/10.
 */
public class TestFuture {

    public static void main(String[] args) {
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("异步逻辑");
                return null;
            }
        });

        new Thread(futureTask).start();
    }

}
