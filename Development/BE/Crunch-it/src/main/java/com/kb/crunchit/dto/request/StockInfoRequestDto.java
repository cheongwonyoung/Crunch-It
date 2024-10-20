package com.kb.crunchit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockInfoRequestDto { // API 요청 시 필요한 값
    private String serviceKey; // 인증키
    private String numOfRows; // 한 페이지 결과 수
    private String pageNo; // 페이지 번호
    private String resultType; // 결과형식 : json
    private String basDt; // 기준일자(매일 오전12시 전 날 데이터 받아옴)
}
