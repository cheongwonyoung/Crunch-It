package com.kb.crunchit.entity;

import lombok.Data;

@Data
public class DepositInfo {
    private String productCode;   // 금융상품 코드
    private String bankName;      // 금융회사 이름
    private String productName;   // 금융상품 이름
    private String joinMethod;    // 가입 방법
}
