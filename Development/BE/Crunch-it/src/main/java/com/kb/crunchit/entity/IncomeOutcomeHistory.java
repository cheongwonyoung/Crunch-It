package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class IncomeOutcomeHistory {
    public int incomeOutcomeHistory;
    public int accountId;
    public int userId;
    public int transactionType;
    public int amount;
    public String registerDate;
}
