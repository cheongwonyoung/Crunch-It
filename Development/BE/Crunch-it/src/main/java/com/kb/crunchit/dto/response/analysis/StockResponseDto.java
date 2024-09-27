package com.kb.crunchit.dto.response.analysis;

import lombok.Data;

@Data
public class StockResponseDto { // 사용자 주식 정보 요청에 대한 응답을 담을 DTO 클래스
    private String stockCode; // 주식 코드
    private int mdUserId; // 사용자의 마이데이터 ID
    private String stockName; // 주식 이름
    private Integer averagePurchasePrice; // 주식 평균단가
    private Integer quantity; // 주식 개수(몇 주)
    private String stockMarket; // 주식 시장(코스피, 코스닥 ...)
}
