package com.kb.crunchit.dto.response.analysis;

import lombok.Data;

@Data
public class BondResponseDto { // 사용자 채권 정보 요청에 대한 응답을 담을 DTO 클래스
    private String bondCode; // 채권 코드
    private int mdUserId; // 사용자의 마이데이터 ID
    private String bondName; // 채권 이름
    private Integer averagePurchasePrice; // 평균 단가
    private Integer quantity; // 채권 개수
    private Integer interestRate; // 채권 이자율
}
