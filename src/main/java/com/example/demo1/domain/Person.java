package com.example.demo1.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString(exclude = "phoneNumber")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int age;
    private String hobby;
    private String address;
    private LocalDate birthday;
    private String bloodType;
    private String job;
    private String phoneNumber;

}