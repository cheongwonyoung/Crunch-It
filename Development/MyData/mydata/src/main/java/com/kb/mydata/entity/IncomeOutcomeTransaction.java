package com.kb.mydata.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class IncomeOutcomeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IncomeOutcomeTransactionId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "userId")
    private MdUser user;

    private Boolean transactionType;

    private Integer amount;

    private LocalDateTime date;
}