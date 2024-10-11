package com.kb.crunchit.entity;

import lombok.Data;

@Data
public class AssetStatistics {
    public int id;
    public int userId;
    public long savingsAmount;
    public long stockInvestAmount;
    public long stockProfitAmount;
    public long fundInvestAmount;
    public long fundProfitAmount;
    public long bondInvestAmount;
    public long bondProfitAmount;
    public long totalAccountBalance;
    public long totalOutcome;
    public String registerDate;
    public String updateDate;
}
