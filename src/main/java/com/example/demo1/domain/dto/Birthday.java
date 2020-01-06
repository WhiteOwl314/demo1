package com.example.demo1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable //Entity에 속해있는 dto
@AllArgsConstructor
@NoArgsConstructor
public class Birthday {
    private int yearOfBirthday;
    private int monthOfBirthday;
    private int dayOfBirthday;
}
