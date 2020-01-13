package com.example.demo1.service;

import com.example.demo1.domain.Person;
import com.example.demo1.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//보통 서비스의 테스트는 Mock테스트를 주로 사용함
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks //테스트의 대상
    private PersonService personService;
    @Mock //대상에서 Autowired 하고 있는 것, data.sql 사용하지 않음 -> 어떻게 동작할지는 이 안에서 지정
    private PersonRepository personRepository;

    @Test
    void getPeopleByName(){
        //호출되었다고 가정
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }


   }
}