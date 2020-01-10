package com.example.demo1.service;

import com.example.demo1.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//보통 서비스의 테스트는 Mock테스트를 주로 사용함
@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    void getPeopleByName(){
        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");

    }

    @Test
    void getPerson(){
        Person person = personService.getPerson((long) 3);

        assertThat(person.getName()).isEqualTo("dennis");
    }
}