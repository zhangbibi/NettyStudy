package com.netty.study.seven.thrift;

import com.netty.study.seven.thrift.generated.Person;
import com.netty.study.seven.thrift.generated.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by zhangyaping on 18/11/25.
 */
public class ThriftClient {

    public static void main(String[] args) {

        TTransport tTransport = new TFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(tTransport);

        PersonService.Client client = new PersonService.Client(protocol);

        try {
            tTransport.open();

            Person person = client.getPersonByUsername("张三");
            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("-----------------");

            Person person2 = new Person();
            person2.setUsername("Fuck");
            person2.setAge(12);
            person2.setMarried(true);

            client.savePerson(person2);

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } finally {
            tTransport.close();
        }

    }
}
