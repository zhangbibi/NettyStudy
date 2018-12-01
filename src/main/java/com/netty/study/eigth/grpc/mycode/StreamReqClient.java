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
import com.netty.study.eigth.grpc.proto.StudentResponseList;
import com.netty.study.eigth.grpc.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * A simple client that requests a greeting from the {@link GRpcServer}.
 */
public class StreamReqClient {
    private static final Logger logger = Logger.getLogger(StreamReqClient.class.getName());


    public static void main(String[] args) throws Exception {
        final CountDownLatch done = new CountDownLatch(1);

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(channel);

        ClientResponseObserver<StudentRequest, StudentResponseList> clientResponseObserver =
                new ClientResponseObserver<StudentRequest, StudentResponseList>() {

                    ClientCallStreamObserver<StudentRequest> requestStream;

                    @Override
                    public void beforeStart(final ClientCallStreamObserver<StudentRequest> requestStream) {
                        this.requestStream = requestStream;
                        requestStream.disableAutoInboundFlowControl();
                        requestStream.setOnReadyHandler(new Runnable() {
                            // An iterator is used so we can pause and resume iteration of the request data.
                            Iterator<Integer> iterator = ages().iterator();

                            @Override
                            public void run() {
                                // Start generating values from where we left off on a non-gRPC thread.
                                while (requestStream.isReady()) {
                                    if (iterator.hasNext()) {
                                        // Send more messages if there are more messages to send.
                                        int age = iterator.next();
                                        logger.info("--> " + age);
                                        StudentRequest request = StudentRequest.newBuilder().setAge(age).build();
                                        requestStream.onNext(request);
                                    } else {
                                        // Signal completion if there is nothing left to send.
                                        requestStream.onCompleted();
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onNext(StudentResponseList value) {
                        logger.info("<-- " + value.getStudentResponseList());
                        // Signal the sender to send one message.
                        requestStream.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        done.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        logger.info("All Done");
                        done.countDown();
                    }
                };

        // Note: clientResponseObserver is handling both request and response stream processing.
        stub.getStudentWrapperByAges(clientResponseObserver);

        done.await();

        channel.shutdown();
        channel.awaitTermination(1, TimeUnit.SECONDS);
    }


    private static List<Integer> ages() {
        return Arrays.asList(20, 21, 22, 23);
    }
}
