package com.kb.crunchit.dto.response.analysis;

import lombok.Data;

@Data
public class FundResponseDto { // 사용자 펀드 정보 요청에 대한 응답을 담을 DTO 클래스
    private String fundCode; // 펀드 코드
    private int mdUserId; // 사용자의 마이데이터 ID
    private String fundType; // 펀드 타입 (주식형, 채권형, ...)
    private String fundName; // 펀드 이름
    private Integer evaluationAmount; // 평가 금액
    private Integer investingAmount; // 투자 금액
}
