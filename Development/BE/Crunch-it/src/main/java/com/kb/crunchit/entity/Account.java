package com.kb.crunchit.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Account {
    public int accountId;
    public int userId;
    public int accountType;
    public String accountNumber;
    public String bankName;
    public String accountName;
    public long balance;

    public List<IncomeOutcomeHistory> incomeOutcomeHistories;

    public void addIncomeOutcomeHistories(List<IncomeOutcomeHistory> histories){
        this.incomeOutcomeHistories = histories;
    }
}
