package com.steadyman.tuto.springbatch.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER")
@Getter
public class Member {

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

}
