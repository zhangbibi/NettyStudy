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
import io.grpc.stub.StreamObserver;

/**
 * A simple client that requests a greeting from the {@link GRpcServer}.
 */
public class StreamReqClient {

    public static void main(String[] args) throws Exception {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(channel);

        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(studentResponse -> {
                    System.out.println(studentResponse.getName());
                    System.out.println(studentResponse.getAge());
                    System.out.println(studentResponse.getCity());
                    System.out.println("*************");
                });
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        };

        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentWrapperByAges(studentResponseListStreamObserver);
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(22).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(23).build());
        studentRequestStreamObserver.onCompleted();

        //客户端以流形式发送数据时必做使用异步的stub   所以这里sleep等待结果返回
        Thread.sleep(10000);
    }


}
