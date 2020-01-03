package com.example.demo1.service;

import com.example.demo1.domain.Block;
import com.example.demo1.domain.Person;
import com.example.demo1.repository.BlockRepository;
import com.example.demo1.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

//보통 서비스의 테스트는 Mock테스트를 주로 사용함
@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks(){
        //현재 데이터베이스에 아무 값이 없기 때문에
        givenPeople();

        List<Person> result = personService.getPeopleExcludeBlocks();

        result.forEach(System.out::println); //?
    }

    @Test
    void cascadeTest(){
        givenPeople();

        List<Person> result = personRepository.findAll();

        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);
        // martin은 삭제가 되었는데 아직 block쪽에는 martin이 남아있음. -> cascadeType.REMOVE 추가

        person.setBlock(null);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);
        // martin은 block이 해제되었지만 blockRepository에는 아직 martin이 남아있음. -> orphanRemoval = true
    }

    private void givenBlockPerson(String name , int age){
        Person blockPerson = new Person(name, age);
        blockPerson.setBlock(new Block(name));
        personRepository.save(blockPerson);
    }

    private void givenPeople() {
        givenPerson("martin",10);
        givenPerson("david", 9);
        givenPerson("dannis", 7);
        givenBlockPerson("martin",11);
    }

    private void givenPerson(String name, int age) {
        personRepository.save(new Person(name, age));
    }
}