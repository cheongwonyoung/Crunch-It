package com.kb.crunchit.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class RecommendationApiSaving {
    public String finPrdtCd;  // fin_prdt_cd -> finPrdtCd
    public Integer recommendationApiSavingId;  // recommendation_api_saving_id -> recommendationApiSavingId
    public String korCoNm;  // kor_co_nm -> korCoNm
    public String finPrdtNm;  // fin_prdt_nm -> finPrdtNm
    public String joinWay;  // join_way -> joinWay

    public List<RecommendationApiSavingOption> sixMonthOption; // 6개월
    public List<RecommendationApiSavingOption> yearOption; // 12개월

    public void addSixMonthOption(List<RecommendationApiSavingOption> savingOption){
        this.sixMonthOption = savingOption;
    }

    public void addYearOption(List<RecommendationApiSavingOption> savingOption){
        this.yearOption = savingOption;
    }
}
