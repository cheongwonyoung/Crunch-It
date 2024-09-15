package com.kb.mydata.entity;

import jakarta.persistence.*;

@Entity
public class UserFund {

    @Id
    private String fundCode;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MdUser user;

    private Integer fundType;

    private String fundName;

    private Integer evaluationAmount;

    private Integer investmentAmount;
}