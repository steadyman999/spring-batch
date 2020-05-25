package com.steadyman.tuto.springbatch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SEX", nullable = false)
    private String sex;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    private Person(String name, String sex, String phone) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }

    public static Person from(String name, String sex, String phone) {
        return new Person(name, sex, phone);
    }

}
