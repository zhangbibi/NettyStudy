package com.netty.study.eigth.grpc.mycode;

import com.netty.study.eigth.grpc.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

/**
 * Created by zhangyaping on 18/12/1.
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    //简单客户端请求服务端响应
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("from client: " + request.getUsername());
        MyResponse response = MyResponse.newBuilder().setRealname("fuck").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    //服务端返回流
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

    //客户端请求流
    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {

        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("onNext : " + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse = StudentResponse.newBuilder().setName("张三").setAge(20).setCity("xian").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setName("李四").setAge(21).setCity("bh").build();

                StudentResponseList studentResponseList = StudentResponseList.newBuilder()
                        .addStudentResponse(studentResponse).addStudentResponse(studentResponse2).build();

                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }

    //双向流式模式
    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println(value.getRequestInfo());
                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
