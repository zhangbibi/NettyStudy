package com.netty.study.sixth.protobuf;

/**
 * Created by zhangyaping on 18/11/24.
 */
public class ProtoBufTest {

    public static void main(String[] args) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("Hello").setAge(21).setAddress("深圳").build();

        byte[] student2ByteArray = student.toByteArray();

        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);

        System.out.println(student2.getName());

    }

}

