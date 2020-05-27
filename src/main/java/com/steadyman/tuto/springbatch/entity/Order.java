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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_NO")
    private Long orderNo;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAYMENT_SEQ")
    private Long paymentSeq;

    @Column(name = "GOODS_NAME")
    private Integer goodsName;

    @Column(name = "GOODS_AMT")
    private Integer goodsAmt;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
