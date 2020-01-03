package com.example.demo1.service;

import com.example.demo1.domain.Block;
import com.example.demo1.domain.Person;
import com.example.demo1.repository.BlockRepository;
import com.example.demo1.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        givenPeaple();
        givenBlocks();

        List<Person> result = personService.getPeopleExcludeBlocks();

//        System.out.println(result);
        result.forEach(System.out::println); //?
    }

    private void givenBlockPerson(String name , int age){
        Person blockPerson = new Person(name, age);
        blockPerson.setBlock(new Block(name));
        personRepository.save(blockPerson);
    }

    private void givenBlocks() {
        givenBlock("martin");
    }

    private Block givenBlock(String name) {
        return blockRepository.save(new Block(name));
    }

    private void givenPeaple() {
        givenPerson("martin",10);
        givenPerson("david", 9);
        givenPerson("dannis", 7);
        givenBlockPerson("martin",11);
    }

    private void givenPerson(String name, int age) {
        personRepository.save(new Person(name, age));
    }
}