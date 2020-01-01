package com.example.demo1.repository;

import com.example.demo1.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    }


}