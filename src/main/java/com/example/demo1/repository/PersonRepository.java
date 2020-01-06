package com.example.demo1.repository;

import com.example.demo1.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);

    List<Person> findByBlockIsNull();

    List<Person> findByBloodType(String bloodType);

    //Entity 기반으로 쿼리 실행
    //?1 은 첫번째 인자를 뜻함
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = ?1 and person.birthday.dayOfBirthday = ?2")
    List<Person> findByMonthOfBirthday(int monthOfBirthday, int dayOfBirthday);
}
