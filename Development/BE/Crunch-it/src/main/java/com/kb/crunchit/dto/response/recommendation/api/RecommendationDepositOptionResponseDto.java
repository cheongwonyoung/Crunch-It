package com.kb.crunchit.dto.response.recommendation.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationDepositOptionResponseDto {
    @JsonProperty("fin_co_no")
    private String finCoNo;           // 금융회사 코드

    @JsonProperty("fin_prdt_cd")
    private String finPrdtCd;         // 금융상품 코드

    @JsonProperty("intr_rate_type")
    private String intrRateType;      // 금리 유형

    @JsonProperty("intr_rate_type_nm")
    private String intrRateTypeNm;    // 금리 유형명

    @JsonProperty("save_trm")
    private String saveTrm;           // 저축 기간

    @JsonProperty("intr_rate")
    private double intrRate;          // 기본 금리

    @JsonProperty("intr_rate2")
    private double intrRate2;         // 최고 우대금리
}
