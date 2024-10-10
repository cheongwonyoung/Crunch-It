package com.kb.crunchit.dto.response.recommendation.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationSavingResponseDto {

    @JsonProperty("fin_co_no")
    private String finCoNo;           // 금융회사 번호

    @JsonProperty("fin_prdt_cd")
    private String finPrdtCd;         // 금융상품 코드

    @JsonProperty("kor_co_nm")
    private String korCoNm;           // 금융회사 이름

    @JsonProperty("fin_prdt_nm")
    private String finPrdtNm;         // 금융상품 이름

    @JsonProperty("join_way")
    private String joinWay;           // 가입 방법

    @JsonProperty("optionList")
    private List<RecommendationSavingOptionResponseDto> optionList; // 옵션 리스트
}
