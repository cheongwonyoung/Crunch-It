package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.IncomeOutcomeTransaction;
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
        private Boolean transactionType;
        private Integer amount;
        private LocalDateTime registerDate;

        public static Info fromEntity(IncomeOutcomeTransaction transaction) {
            return Info.builder()
                    .transactionId(transaction.getIncomeOutcomeTransactionId())
                    .userId(transaction.getUser().getUserId())
                    .accountId(transaction.getAccount().getAccountId()) // 계좌 ID
                    .transactionType(transaction.getTransactionType())
                    .amount(transaction.getAmount())
                    .registerDate(transaction.getRegisterDate())
                    .build();
        }
    }
}