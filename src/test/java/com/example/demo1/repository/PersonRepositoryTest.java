package com.example.demo1.repository;

import com.example.demo1.domain.Person;
import com.example.demo1.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByName(){

        List<Person> people = personRepository.findByName("tony");
        assertThat(people.size()).isEqualTo(1);

        assertAll(
                () -> assertThat(people.get(0).getName()).isEqualTo("tony"),
                () -> assertThat(people.get(0).getBirthday()).isEqualTo(Birthday.of(LocalDate.of(1991,7,10))),
                () -> assertThat(people.get(0).getPhoneNumber()).isEqualTo("010-3925-1533"),
                () -> assertThat(people.get(0).getAddress()).isEqualTo("대전"),
                () -> assertThat(people.get(0).getJob()).isEqualTo("officer"),
                () -> assertThat(people.get(0).getHobby()).isEqualTo("reading")
        );
    }

    @Test
    void findByMonthOfBirthday(){

        List<Person> people = personRepository.findByMonthOfBirthday(7);

        assertThat(people.size()).isEqualTo(2);

        assertAll(
                () -> assertThat(people.get(0).getName()).isEqualTo("david"),
                () -> assertThat(people.get(1).getName()).isEqualTo("tony")
        );
    }

    @Test
    void findPeopleDeleted(){

        List<Person> people = personRepository.findPeopleDeleted();

        assertThat(people.size()).isEqualTo(1);

        assertThat(people.get(0).getName()).isEqualTo("andrew");
    }
}