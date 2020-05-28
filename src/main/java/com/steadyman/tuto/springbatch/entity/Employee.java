package com.steadyman.tuto.springbatch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "DEPARTMENT_SEQ")
    private String departmentSeq;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SALARY")
    private Integer salary;
}
