package com.example.demo1.repository;

import com.example.demo1.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println(person2.hashCode());

        //해쉬코드가 다른 경우 문제점
        //1. Map에서 해쉬코드가 다르기 때문에 같은값이 나오지 않음
        //Map : dictionary 같이 key - valure 로 이루어져 있는 자료형
        Map<Person, Integer> map = new HashMap<>();
        //put : 값을 넣어줌
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));
    }
}