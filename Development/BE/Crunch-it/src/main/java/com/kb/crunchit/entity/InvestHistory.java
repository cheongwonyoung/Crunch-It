package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class InvestHistory {
    public int id;
    public int user_id;
    public long savings_amount;
    public long stock_invest_amount;
    public long stock_profit_amount;
    public long fund_invest_amount;
    public long fund_profit_amount;
    public long bond_invest_amount;
    public long bond_profit_amount;
    public long total_account_balance;
    public long total_outcome;
    public String register_date;
}
