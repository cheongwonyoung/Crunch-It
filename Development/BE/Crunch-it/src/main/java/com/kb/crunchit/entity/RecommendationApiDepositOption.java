package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class RecommendationApiDepositOption {
    public Integer recommendationApiDepositOptionId;  // recommendation_api_deposit_option_id -> recommendationApiDepositOptionId
    public String finPrdtCd;  // fin_prdt_cd -> finPrdtCd
    public String intrRateTypeNm;  // intr_rate_type_nm -> intrRateTypeNm
    public Integer saveTrm;  // save_trm -> saveTrm
    public Double intrRate;  // intr_rate -> intrRate (DECIMAL(5,2) -> Double)
    public Double intrRate2;
}