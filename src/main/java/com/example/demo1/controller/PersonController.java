package com.example.demo1.controller;

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
    public void modifyPerson(@PathVariable Long id, @RequestBody Person person){
        personService.modify(id,person);
        log.info("person -> {}" , personRepository.findAll());
    }
}
