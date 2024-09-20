package com.kb.mydata.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

@Entity
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;
    private Integer accountType;

    private String accountNumber;

    private String bankName;

    private String accountName;

    private long balance;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MdUser user;

    @OneToMany(mappedBy = "account")
    private List<IncomeOutcomeTransaction> transactions;

}