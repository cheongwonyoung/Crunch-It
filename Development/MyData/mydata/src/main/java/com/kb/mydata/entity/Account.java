package com.kb.mydata.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    private Integer accountType;

    private String accountNumber;

    private String bankName;

    private String accountName;

    private BigInteger balance;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MdUser user;

    @OneToMany(mappedBy = "account")
    private List<IncomeOutcomeTransaction> transactions;

}