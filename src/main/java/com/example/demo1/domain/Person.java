package com.example.demo1.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    @OneToOne
    private Block block;
}
