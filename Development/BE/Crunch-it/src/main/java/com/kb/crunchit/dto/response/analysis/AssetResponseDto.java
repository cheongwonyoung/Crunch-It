package com.kb.crunchit.dto.response.analysis;

import lombok.Data;

@Data
public class AssetResponseDto {
    private long totalAccountBalance;
    private long savingsAmount;
    private long stockInvestAmount;
    private long fundInvestAmount;
    private long bondInvestAmount;
    private long stockProfitAmount;
    private long fundProfitAmount;
    private long bondProfitAmount;
    private long totalOutcome;
}
