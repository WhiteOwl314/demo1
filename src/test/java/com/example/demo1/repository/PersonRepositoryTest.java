package com.example.demo1.repository;

import com.example.demo1.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void crud(){
        Person person = new Person();

        person.setName("martin");
        person.setAge(10);

        personRepository.save(person);

        System.out.println(personRepository.findAll());

        List<Person> people = personRepository.findAll();

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("martin");
        assertThat(people.get(0).getAge()).isEqualTo(10);
    }

    //HashCode, Equals Test
    @Test
    void hashCodeAndEquals(){
        Person person1 = new Person("martin", 10);
        Person person2 = new Person("martin", 10);

        //equals 를 override 안해주면 false가 나옴
        System.out.println(person1.equals(person2));

        //hasgcode 는 다른 값을 가
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());짐
    }
}