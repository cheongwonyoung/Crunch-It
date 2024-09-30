package com.kb.crunchit.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Account {
    public int account_id;
    public int user_id;
    public int account_type;
    public String account_number;
    public String bank_name;
    public String account_name;
    public long balance;

    public List<IncomeOutcomeHistory> incomeOutcomeHistories;

    public void addIncomeOutcomeHistories(List<IncomeOutcomeHistory> histories){
        this.incomeOutcomeHistories = histories;
    }
}
