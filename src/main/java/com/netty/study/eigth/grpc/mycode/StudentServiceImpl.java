package com.netty.study.eigth.grpc.mycode;

import com.netty.study.eigth.grpc.proto.MyRequest;
import com.netty.study.eigth.grpc.proto.MyResponse;
import com.netty.study.eigth.grpc.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Created by zhangyaping on 18/12/1.
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("from client: "+ request.getUsername());
        MyResponse response = MyResponse.newBuilder().setRealname("fuck").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
