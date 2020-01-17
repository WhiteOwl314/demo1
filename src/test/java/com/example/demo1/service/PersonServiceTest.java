package com.example.demo1.service;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.domain.Person;
import com.example.demo1.domain.dto.Birthday;
import com.example.demo1.exception.PersonNotFoundException;
import com.example.demo1.exception.RenameNotPermittedException;
import com.example.demo1.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//보통 서비스의 테스트는 Mock테스트를 주로 사용함
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks //테스트의 대상
    private PersonService personService;
    @Mock //대상에서 Autowired 하고 있는 것, data.sql 사용하지 않음 -> 어떻게 동작할지는 이 안에서 지정
    private PersonRepository personRepository;

    @Test
    void getAll(){
        when(personRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Lists.newArrayList(new Person("martin"),new Person("denis"),new Person("tony"))));

        Page<Person> result = personService.getAll(PageRequest.of(0,3));

        assertThat(result.getNumberOfElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getName()).isEqualTo("martin");
        assertThat(result.getContent().get(1).getName()).isEqualTo("denis");
        assertThat(result.getContent().get(2).getName()).isEqualTo("tony");
    }

    @Test
    void getPeopleByName(){
        //호출되었다고 가정
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("martin");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put(){

        personService.put(mockPersonDto());

        verify(personRepository,times(1)).save(argThat(new IsPersonWllBeInserted()));
    }

    @Test
    void modifyIfPersonNotFound(){

        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modifyIfNameIsDifferent(){

        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("tony")));

        assertThrows(RenameNotPermittedException.class , () -> personService.modify(1L,mockPersonDto()));
    }

    @Test
    void modify(){

        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,mockPersonDto());

        verify(personRepository,times(1)).save(any(Person.class));
        verify(personRepository,times(1)).save(argThat(new IsPersonWillBeUpdated()));
    }

    @Test
    void modifyByNameIfPersonIsNotFound(){
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.modify(1L,"danial"));
    }

    @Test
    void modifyByName(){

        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        personService.modify(1L,"danial");

        verify(personRepository, times(1)).save(argThat(new IsNameWillBeupdated()));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin","programming","대전", LocalDate.now(),"programmer","010-3925-1533");
    }

    @Test
    void deleteIfPersonNotFound(){

        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.delete(1L));
    }

    @Test
    void delete(){

        when(personRepository.findById(1L)).thenReturn(Optional.of(new Person("martin")));

        personService.delete(1L);

        verify(personRepository, times(1)).save(argThat(new IsPersonWillBeDeleted()));
    }

    private static class  IsPersonWllBeInserted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals( person.getHobby(), "programming")
                    && equals(person.getAddress(), "대전")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(),"010-3925-1533");
        }
        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }

    }

    private static class IsPersonWillBeUpdated implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person person) {
            return equals(person.getName(), "martin")
                    && equals( person.getHobby(), "programming")
                    && equals(person.getAddress(), "대전")
                    && equals(person.getBirthday(), Birthday.of(LocalDate.now()))
                    && equals(person.getJob(), "programmer")
                    && equals(person.getPhoneNumber(),"010-3925-1533");
        }
        private boolean equals(Object actual, Object expected){
            return expected.equals(actual);
        }

    }

    private static class IsNameWillBeupdated implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.getName().equals("danial");
        }
    }

    private static class IsPersonWillBeDeleted implements ArgumentMatcher<Person>{

        @Override
        public boolean matches(Person person) {
            return person.isDeleted();
        }
    }
}