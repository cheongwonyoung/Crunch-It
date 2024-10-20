package com.kb.crunchit.entity;

import lombok.Data;

@Data
public class BondInfo {
    private String issuerName;       // 발행자 이름
    private String productName;      // 채권 이름 (ISIN 코드명)
    private String creditRating;     // 신용 등급
    private String surfaceRate;      // 표면 금리
    private String interestType;     // 이자 유형
    private String maturityDate;     // 만기일
    private String interestPaymentCycle; // 이자 지급 주기
}
