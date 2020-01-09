package com.example.demo1.controller;

import com.example.demo1.domain.Person;
import com.example.demo1.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService; //PersonService bean injection

    @GetMapping
    public Person getPerson(Long id){
        return personService.getPerson(id);
    }
}
