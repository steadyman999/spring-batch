package com.steadyman.tuto.springbatch.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT_HISTORY")
@Getter
@Setter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
