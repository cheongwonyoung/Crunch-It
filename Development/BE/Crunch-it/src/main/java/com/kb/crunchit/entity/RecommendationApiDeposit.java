package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class RecommendationApiDeposit {
    public String finPrdtCd;  // fin_prdt_cd -> finPrdtCd

    public Integer recommendationApiDepositId;  // recommendation_api_deposit_id -> recommendationApiDepositId

    public String korCoNm;  // kor_co_nm -> korCoNm

    public String finPrdtNm;  // fin_prdt_nm -> finPrdtNm

    public String joinWay;  // join_way -> joinWay

    public RecommendationApiDepositOption sixMonthOption; // 6개월
    public RecommendationApiDepositOption yearOption; // 12개월

    public void addSixMonthOption(RecommendationApiDepositOption depositOption){
        this.sixMonthOption = depositOption;
    }

    public void addYearOption(RecommendationApiDepositOption depositOption){
        this.yearOption = depositOption;
    }

}