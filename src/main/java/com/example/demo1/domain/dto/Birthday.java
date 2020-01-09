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
    //반복되는 값 써주기
    private static LocalDate today = LocalDate.now();
    // 오늘 23:50분에 객체가 생성되어 today가 오늘이 되었는데 내일 생일이라면 생일 오류 발생

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

    //생일을 통해서 나이 구하기
    public int getAge(){
        return today.getYear() - this.yearOfBirthday + 1;
    }

    //생일인지 아닌지 알려주는 로직
    public boolean isBirthdayToday(){
        return today.equals(LocalDate.of(yearOfBirthday,monthOfBirthday,dayOfBirthday));
    }


    public static Birthday of(LocalDate birthday){
        return new Birthday(birthday);
    }
}
