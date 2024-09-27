package com.kb.crunchit.dto.response.analysis;

import lombok.Data;

@Data
public class AccountResponseDto { // 사용자 계좌 정보 요청에 대한 응답을 담을 DTO 클래스
    private int accountId; // 계좌 ID
    private int mdUserId; // 사용자의 마이데이터 ID
    private String accountType; // 계좌 종류(보통예금, 정기예금, 적금, 증권)
    private String accountNumber; // 계좌번호
    private String bankName; // 은행명
    private String accountName; // 계좌 이름
    private long balance; // 잔액
}
