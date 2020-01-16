package com.example.demo1.controller;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.domain.Person;
import com.example.demo1.exception.PersonNotFoundException;
import com.example.demo1.exception.RenameNotPermittedException;
import com.example.demo1.exception.dto.ErrorResponse;
import com.example.demo1.repository.PersonRepository;
import com.example.demo1.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RequestMapping(value = "/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService; //PersonService bean injection

    //조회하는 API
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    //저장하는 API
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201: created
    public void postPerson(@RequestBody PersonDto personDto){
        //Post 방법에서 RequestBody로 받는것은 그리 안전한 방법이 아님.
        //이유: Person Entity 에서 id,deleted 는 사용자가 입력하는게 아니기 때문
        //Entity를 직접 쓰지 않고 객체를 받아서 Entity로 변환이 가장 많음
        personService.put(personDto);
    }

    //수정하는 API
    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        personService.modify(id,personDto);
}

    //이름이 정말 바껴야 하는 경우
    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, String name){
        personService.modify(id, name);
}

    //삭제하는 API
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
    }


}