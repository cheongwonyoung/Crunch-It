package com.kb.crunchit.dto.response.recommendation.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationFundResponseDto{
    private String basDt;        // 기준일자
    private String srtnCd;       // 단축코드
    private String fndNm;        // 펀드명
    private String ctg;          // 카테고리
    private String setpDt;       // 설정일자
    private String fndTp;        // 펀드유형
    private String prdClsfCd;    // 상품분류코드
    private String asoStdCd;     // 자산표준코드

}
