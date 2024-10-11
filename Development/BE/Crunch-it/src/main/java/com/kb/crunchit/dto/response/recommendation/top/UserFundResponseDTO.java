package com.kb.crunchit.dto.response.recommendation.top;

public class UserFundResponseDTO {
    private String fundCode;
    private Long userId;
    private String fundType;
    private String fundName;
    private Long evaluationAmount;
    private Long investingAmount;

    // Getters and Setters
    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public Long getEvaluationAmount() {
        return evaluationAmount;
    }

    public void setEvaluationAmount(Long evaluationAmount) {
        this.evaluationAmount = evaluationAmount;
    }

    public Long getInvestingAmount() {
        return investingAmount;
    }

    public void setInvestingAmount(Long investingAmount) {
        this.investingAmount = investingAmount;
    }
}
