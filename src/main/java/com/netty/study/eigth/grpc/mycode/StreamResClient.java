/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netty.study.eigth.grpc.mycode;

import com.netty.study.eigth.grpc.proto.StudentRequest;
import com.netty.study.eigth.grpc.proto.StudentResponse;
import com.netty.study.eigth.grpc.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple client that requests a greeting from the {@link GRpcServer}.
 */
public class StreamResClient {
    private static final Logger logger = Logger.getLogger(StreamResClient.class.getName());

    private final ManagedChannel channel;
    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;

    public StreamResClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = StudentServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        try {
            Iterator<StudentResponse> it = blockingStub.getStudentByAge(StudentRequest.newBuilder().setAge(20).build());
            while (it.hasNext()) {
                StudentResponse res = it.next();
                System.out.println(res.getName() + " " + res.getAge() + " " + res.getCity());
            }

        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }

    }

    public static void main(String[] args) throws Exception {
        StreamResClient client = new StreamResClient("localhost", 50051);
        try {
            String user = "world";
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}
