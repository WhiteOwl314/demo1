package com.example.demo1.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;
    private String hobby;
    private String address;
    private LocalDate birthday;
    private String bloodType;
    private String job;

    @ToString.Exclude
    private String phoneNumber;


    //차단할 수 있는 기능
    //isBlock 자동으로 만들어줌
    // 너무 많은 기능들 -> 도메인으로 넘길 예정
    private boolean block;

    private String blockReason;

    private LocalDate blockStartDate;

    private LocalDate blockEndDate;
}
