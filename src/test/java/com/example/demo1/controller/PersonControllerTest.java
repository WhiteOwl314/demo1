package com.example.demo1.controller;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@Transactional
class PersonControllerTest {

    @Autowired
    PersonController personController;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ObjectMapper objectMapper;



    private MockMvc mockMvc;

    //반복되는 부분 리펙토링
    @BeforeEach //매 테스트마다 한번씩 실행됨
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void getPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
                //isOk : 200응답
    }

    @Test
    void postPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person?name=martin2&age=20&bloodType=A")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("{\n" +
                            "  \"name\": \"martin2\",\n" +
                            "  \"age\": 20, \n" +
                            "  \"bloodType\" : \"A\"\n" +
                            "}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void modifyPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\n" +
                        "  \"name\": \"martin\",\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk()); //put 은 200으로 세팅
    }

    //이름만 바뀌는 것 테스트
    @Test
    void modifyName() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .param("name","martinModified"))//네임이 하나여서
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }

    @Test
    void deletePerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk()); //200 인것만 검증하고 있음 , 실제로 지워졌는지 지워지지 않았는지 로직 없음

        //검증방법
        //1. 컨트롤러에서 void 리턴하지 않고 정상적인 값 리턴해주도록
        //2. Controller에서 요청한 사람이 deleted에 체크된 사람이 있는지 true false 로 알려주는 로직
        //3. PersonControllerTest 에서 직접 personRepository의 삭제된 명단 검토

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));

    }

    @Test
    void checkJasonString() throws JsonProcessingException{
        PersonDto dto = new PersonDto();
        dto.setName("martin");
        dto.setBirthday(LocalDate.now());
        dto.setAddress("판교");

        System.out.println(">>>" + toJasonString(dto));
    }

    private String toJasonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}