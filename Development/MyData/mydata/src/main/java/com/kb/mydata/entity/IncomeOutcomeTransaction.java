package com.kb.mydata.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
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

    private LocalDateTime registerDate;
}