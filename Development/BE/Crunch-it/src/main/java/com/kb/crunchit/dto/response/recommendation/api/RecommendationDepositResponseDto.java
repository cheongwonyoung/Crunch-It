package com.kb.crunchit.dto.response.recommendation.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationDepositResponseDto {
    @JsonProperty("fin_co_no")
    private String finCoNo;

    @JsonProperty("fin_prdt_cd")
    private String finPrdtCd;

    @JsonProperty("kor_co_nm")
    private String korCoNm;

    @JsonProperty("fin_prdt_nm")
    private String finPrdtNm;

    @JsonProperty("join_way")
    private String joinWay;

    @JsonProperty("dcls_month")
    private String dclsMonth;

    // 예금 옵션 리스트
    @JsonProperty("optionList")
    private List<RecommendationDepositOptionResponseDto> optionList;
}
