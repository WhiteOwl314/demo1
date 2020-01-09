package com.example.demo1.domain;

import com.example.demo1.domain.dto.Birthday;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    @NotEmpty //String 타입에게
    private String name;

    @NonNull
    @Min(1)
    private int age;

    private String hobby;
    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String bloodType;
    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Block block;
    //optional = true : 블록의 값은 항상 필요하다
}
