package com.example.demo1.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
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

    public boolean equals(Object object){
        if(object == null){
            return false;
        }

        //다운캐스팅
        Person person = (Person) object;

        if(!person.getName().equals(this.getName())){
            return false;
        }

        if(person.getAge() != this.getAge()){
            return false;
        }

        return true;

    }

    @Override
    public int hashCode() {
        return  (name + age).hashCode(); //name + age 는 임의로 설정해준 것
    }
}
