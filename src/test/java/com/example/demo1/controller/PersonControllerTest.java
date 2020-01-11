package com.example.demo1.controller;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.domain.Person;
import com.example.demo1.domain.dto.Birthday;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;



    private MockMvc mockMvc;

    //반복되는 부분 리펙토링
    @BeforeEach //매 테스트마다 한번씩 실행됨
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).setMessageConverters(messageConverter).build();
    }

    @Test
    void getPerson() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("hobby").isEmpty())
                .andExpect(jsonPath("address").isEmpty())
                .andExpect(jsonPath("$.birthday.yearOfBirthday").value(1991))
                .andExpect(jsonPath("$.birthday.monthOfBirthday").value(8))
                .andExpect(jsonPath("$.birthday.dayOfBirthday").value(15))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber());

        //get API 에서 리턴되는 값은 Json형식
        //jsonPath로 검증
        //$ 는 객체를 의미
        //value : 갑 가져오고 검증하는것 까지
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

        PersonDto dto = PersonDto.of("martin","programming","대전",LocalDate.now(),"programmer","010-3925-1533");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJasonString(dto)))
                .andDo(print())
                .andExpect(status().isOk()); //put 은 200으로 세팅

        Person result = personRepository.findById(1L).get();

        assertAll(
                ()-> assertThat(result.getName()).isEqualTo("martin"),
                ()-> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("대전"),
                () ->assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () ->  assertThat(result.getJob()).isEqualTo("programmer")
        );
    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception{

        PersonDto dto = PersonDto.of("Seongju","programming","대전",LocalDate.now(),"programmer","010-3925-1533");

        assertThrows(NestedServletException.class, () ->

            mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/person/1")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(toJasonString(dto)))
                    .andDo(print())
                    .andExpect(status().isOk()) //put 은 200으로 세팅
        );
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

    private String toJasonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}