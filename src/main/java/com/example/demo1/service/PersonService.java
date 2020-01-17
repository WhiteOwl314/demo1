package com.example.demo1.service;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.domain.Person;
import com.example.demo1.exception.PersonNotFoundException;
import com.example.demo1.exception.RenameNotPermittedException;
import com.example.demo1.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPeopleByName(String name) {
        return personRepository.findByName(name);
    }

    public Page<Person> getAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        return personRepository.findById(id).orElse(null);
    }

    //save 하는 메소드
    @Transactional
    public void put(PersonDto personDto){
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto){

        //아이디가 존재하면 personAtDb에 넣어준다.
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());

        //가져온 데이터의 이름과 수정할 데이터의 이름이 다르면 경고메세지 출력
        if(!person.getName().equals(personDto.getName())){
            throw new RenameNotPermittedException();
        }

        person.set(personDto);

        //저장
        personRepository.save(person);
    }
    //modify 이름 바꿔야 하는 경우 오버라이딩

    @Transactional
    public void modify(Long id, String name){
       Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());

       person.setName(name);

       personRepository.save(person);
    }

    @Transactional
    public void delete(Long id){
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException());

        person.setDeleted(true);

        personRepository.save(person);
    }
}
