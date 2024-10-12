package com.kb.crunchit.entity;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RecommendationApiSavingOption {
    public Integer recommendationApiSavingOptionId;  // recommendation_api_saving_option_id -> recommendationApiSavingOptionId
    public String finPrdtCd;  // fin_prdt_cd -> finPrdtCd
    public String intrRateTypeNm;  // intr_rate_type_nm -> intrRateTypeNm
    public String rsrvTypeNm;  // rsrv_type_nm -> rsrvTypeNm
    public Integer saveTrm;  // save_trm -> saveTrm
    public Double intrRate;  // intr_rate -> intrRate
    public Double intrRate2;  // intr_rate2 -> intrRate2
}