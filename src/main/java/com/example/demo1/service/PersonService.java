package com.example.demo1.service;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.domain.Person;
import com.example.demo1.domain.dto.Birthday;
import com.example.demo1.repository.BlockRepository;
import com.example.demo1.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        return personRepository.findByBlockIsNull();
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){

        Person person = personRepository.findById(id).orElse(null);

        log.info("person : {}", person);

        return person;
    }

    //save 하는 메소드
    @Transactional
    public void put(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto){

        //아이디가 존재하면 personAtDb에 넣어준다.
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        //가져온 데이터의 이름과 수정할 데이터의 이름이 다르면 경고메세지 출력
        if(!person.getName().equals(personDto.getName())){
            throw new RuntimeException("이름이 다릅니다.");
        }

        person.set(personDto);

        //저장
        personRepository.save(person);
    }

    //modify 이름 바꿔야 하는 경우 오버라이딩
    @Transactional
    public void modify(Long id, String name){
       Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

       person.setName(name);

       personRepository.save(person);
    }

    @Transactional
    public void delete(Long id){
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setDeleted(true);

        personRepository.save(person);
    }
}
