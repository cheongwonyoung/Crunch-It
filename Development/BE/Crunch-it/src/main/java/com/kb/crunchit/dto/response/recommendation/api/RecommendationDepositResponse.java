package com.kb.crunchit.dto.response.recommendation.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)  // 예상치 못한 필드를 무시
public class RecommendationDepositResponse {
    private Result result;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)  // Result 클래스에서 알 수 없는 필드 무시
    public static class Result {
        private String prdtDiv;       // 상품 구분
        private int totalCount;       // 총 개수
        private int maxPageNo;        // 최대 페이지 번호
        private int nowPageNo;        // 현재 페이지 번호
        private String errCd;         // 에러 코드
        private String errMsg;        // 에러 메시지
        private List<RecommendationDepositResponseDto> baseList; // 상품 목록
        private List<RecommendationDepositOptionResponseDto> optionList; // 옵션 리스트 추가
    }
}
