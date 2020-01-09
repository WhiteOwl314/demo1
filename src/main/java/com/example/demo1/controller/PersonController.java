package com.example.demo1.controller;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.domain.Person;
import com.example.demo1.repository.PersonRepository;
import com.example.demo1.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService; //PersonService bean injection
    @Autowired
    private PersonRepository personRepository;

    //조회하는 API
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    //저장하는 API
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201: created
    public void postPerson(@RequestBody Person person){

        personService.put(person);

        log.info("person -> {}" , personRepository.findAll());
    }

    //수정하는 API
    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        personService.modify(id,personDto);
        log.info("person -> {}" , personRepository.findAll());
    }

    //이름이 정말 바껴야 하는 경우
    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, String name){

        personService.modify(id, name);
        log.info("person -> {}" , personRepository.findAll());
    }

    //삭제하는 API
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
        log.info("person -> {}" , personRepository.findAll());
    }
}
