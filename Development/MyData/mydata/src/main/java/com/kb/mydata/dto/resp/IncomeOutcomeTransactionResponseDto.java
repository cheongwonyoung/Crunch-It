package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.UserIncomeOutcomeTransaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class IncomeOutcomeTransactionResponseDto {
    @Data
    @Builder
    public static class Info {
        private int transactionId;  // IncomeOutcomeTransactionId
        private int userId;
        private int accountId;       // Account의 accountId
        private byte transactionType;
        private Integer amount;
        private LocalDateTime registerDate;

        public static Info fromEntity(UserIncomeOutcomeTransaction transaction) {
            return Info.builder()
                    .transactionId(transaction.getTransactionId())
                    .userId(transaction.getUser().getUserId())
                    .accountId(transaction.getAccount().getAccountId()) // 계좌 ID
                    .transactionType(transaction.getTransactionType())
                    .amount(transaction.getAmount())
                    .registerDate(transaction.getRegisterDate())
                    .build();
        }
    }
}