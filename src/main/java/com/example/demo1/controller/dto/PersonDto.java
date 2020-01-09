package com.example.demo1.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDto {

    private String name;
    private int age;
    private String hobby;
    private String address;
    private LocalDate birthday;
    private String bloodType;
    private String job;
    private String phoneNumber;
}
