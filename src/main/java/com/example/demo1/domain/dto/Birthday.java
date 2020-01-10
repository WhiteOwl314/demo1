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

    private Integer yearOfBirthday;
    //LocalDate 사용하고 있기 때문에 min, max 필요없음
    private Integer monthOfBirthday;
    private Integer dayOfBirthday;

    //생성자를 밖에서 접근 못하도록 제어
    private Birthday(LocalDate birthday){
        this.yearOfBirthday = birthday.getYear();
        this.monthOfBirthday = birthday.getMonthValue();
        this.dayOfBirthday = birthday.getDayOfMonth();
    }




    public static Birthday of(LocalDate birthday){
        return new Birthday(birthday);
    }
}
