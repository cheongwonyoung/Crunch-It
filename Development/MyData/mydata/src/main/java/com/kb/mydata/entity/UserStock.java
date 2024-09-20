package com.kb.mydata.entity;

import jakarta.persistence.*;

@Entity
public class UserStock {
    @Id
    private String stockCode;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MdUser user;

    private String stockName;

    private Integer averagePurchasePrice;

    private Integer quantity;

    private String stockMarket;

}