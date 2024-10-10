package com.kb.crunchit.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SavingOptionInfo {
    private String productCode;   // 금융상품 코드
    private String rateTypeName;  // 금리 유형명
    private String reserveTypeName;  // 저축 유형명
    private int saveTerm;         // 저축 기간
    private BigDecimal baseRate;  // 기본 금리
    private BigDecimal maxRate;   // 최고 우대금리
}
