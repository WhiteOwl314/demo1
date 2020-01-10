package com.example.demo1.domain;

import com.example.demo1.controller.dto.PersonDto;
import com.example.demo1.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

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
@Where(clause = "deleted = false") //deleted = false 인 값만 검색되도록
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    @NotEmpty //String 타입에게
    private String name;

    private String hobby;
    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String bloodType;
    private String job;

    @ToString.Exclude
    private String phoneNumber;

    @ColumnDefault("0")//false를 가리ㅊ키는 것
    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Block block;
    //optional = true : 블록의 값은 항상 필요하다

    public void set(PersonDto personDto){
        if(!StringUtils.isEmpty(personDto.getHobby())){
            this.setHobby(personDto.getHobby());
        }
        if(!StringUtils.isEmpty(personDto.getBloodType())){
            this.setBloodType(personDto.getBloodType());
        }
        if(!StringUtils.isEmpty(personDto.getAddress())){
            this.setAddress(personDto.getAddress());
        }
        if(!StringUtils.isEmpty(personDto.getJob())){
            this.setJob(personDto.getJob());
        }
        if(!StringUtils.isEmpty(personDto.getPhoneNumber())){
            this.setPhoneNumber(personDto.getPhoneNumber());
        }
    }

    //생일을 통해서 나이 구하기
    public Integer getAge(){
        if(this.birthday != null){
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        } else {
            return null;
        }
    }

    //생일인지 아닌지 알려주는 로직
    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(),this.birthday.getMonthOfBirthday(),this.birthday.getDayOfBirthday()));
    }
}
