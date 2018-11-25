package com.netty.study.seven.thrift;

import com.netty.study.seven.thrift.generated.DataException;
import com.netty.study.seven.thrift.generated.Person;
import com.netty.study.seven.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * Created by zhangyaping on 18/11/25.
 */
public class PersonServiceImpl implements PersonService.Iface {


    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Got Client params: " + username);
        Person person = new Person();
        person.setUsername(username);
        person.setAge(22);
        person.setMarried(false);

        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Got Client params: ");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
