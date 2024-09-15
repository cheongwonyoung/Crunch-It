package com.kb.mydata.entity;

import jakarta.persistence.*;

@Entity
public class UserBond {

    @Id
    private String bondCode;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MdUser user;

    private String bondName;

    private Integer averagePurchasePrice;

    private Integer quantity;

    private Integer interestRate;

}