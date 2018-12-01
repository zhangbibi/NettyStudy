package com.netty.study.eigth.grpc.mycode;

import com.netty.study.eigth.grpc.proto.*;
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

    @Override
    public void getStudentByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("from client: " + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(21).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("Hello").setAge(22).setCity("深圳").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("Kitty").setAge(23).setCity("南山").build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {

        return null;
    }
}
