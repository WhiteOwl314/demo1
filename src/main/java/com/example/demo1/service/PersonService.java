package com.example.demo1.service;

import com.example.demo1.domain.Person;
import com.example.demo1.repository.BlockRepository;
import com.example.demo1.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopleByName(String name) {
        return personRepository.findByName(name);
    }

    //차단된 사람 외에 전체 사람을 가져오는 로직
    public List<Person> getPeopleExcludeBlocks(){
        List<Person> people = personRepository.findAll();

        return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();

        //배포시 모든 로그 다 출력됨
        System.out.println("person : " + person);
        //배포시 로그 출력 제한 가능
        log.info("person : {}", person);

        return person;
    }
}
