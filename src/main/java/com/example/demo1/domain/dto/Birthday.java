package com.example.demo1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Embeddable //Entity에 속해있는 dto
@Data
@NoArgsConstructor
public class Birthday {
    private int yearOfBirthday;

    @Min(1)
    @Max(12)
    private int monthOfBirthday;

    @Min(1)
    @Max(31)
    private int dayOfBirthday;

    public Birthday(LocalDate birthday){
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }
}
