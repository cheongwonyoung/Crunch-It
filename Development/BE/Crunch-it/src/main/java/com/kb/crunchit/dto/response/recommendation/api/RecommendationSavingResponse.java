package com.kb.crunchit.dto.response.recommendation.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationSavingResponse {
    private Result result;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private String prdtDiv;       // 상품 구분
        private int totalCount;       // 총 개수
        private int maxPageNo;        // 최대 페이지 번호
        private int nowPageNo;        // 현재 페이지 번호
        private String errCd;         // 에러 코드
        private String errMsg;        // 에러 메시지
        private List<RecommendationSavingResponseDto> baseList; // 기본 정보 목록
        private List<RecommendationSavingOptionResponseDto> optionList; // 옵션 정보 목록
    }
}
