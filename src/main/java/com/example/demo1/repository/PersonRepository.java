package com.example.demo1.repository;

import com.example.demo1.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);

    List<Person> findByBlockIsNull();

    List<Person> findByBloodType(String bloodType);

    //Entity 기반으로 쿼리 실행
    //?1 은 첫번째 인자를 뜻함, 인자의 순서대로 검색
    @Query(value = "select person from Person person where person.birthday.monthOfBirthday = :monthOfBirthday")
    List<Person> findByMonthOfBirthday(@Param("monthOfBirthday") int monthOfBirthday );

    //삭제표시한 사람 볼 수  있는 방법
    @Query(value = "SELECT person FROM Person person WHERE person.deleted = true ")
    List<Person> findPeopleDeleted();

}
