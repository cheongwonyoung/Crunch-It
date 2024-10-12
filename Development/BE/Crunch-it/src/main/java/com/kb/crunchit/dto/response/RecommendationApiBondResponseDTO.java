package com.kb.crunchit.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationApiBondResponseDTO {
    public String issuerName;       // 발행자 이름
    public String productName;      // 채권 이름 (ISIN 코드명)
    public String creditRating;     // 신용 등급
    public String surfaceRate;      // 표면 금리
    public String interestType;     // 이자 유형
    public String maturityDate;     // 만기일
    public String interestPaymentCycle; // 이자 지급 주기
}
