package com.kb.crunchit.dto.response.analysis;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponseDto { // 사용자 입출금내역 정보 요청에 대한 응답을 담을 DTO 클래스
    private int transactionId; // 거래 ID
    private int mdUserId; // 사용자의 마이데이터 ID
    private int accountId; // 계좌 ID
    private Boolean transactionType;  // false(0): 입금, true(1): 출금
    private Integer amount; // 입출금 금액
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registerDate; // 등록일(결제일)
}
