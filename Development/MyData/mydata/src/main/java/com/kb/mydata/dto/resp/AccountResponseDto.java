package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.Account; // Account 엔티티를 임포트합니다.
import lombok.Builder;
import lombok.Data;

public class AccountResponseDto {
    @Data
    @Builder
    public static class Info {
        private int accountId;
        private int userId;
        private int accountType;
        private String accountNumber;
        private String bankName;
        private String accountName;
        private long balance;

        public static Info fromEntity(Account account) {
            return Info.builder()
                    .accountId(account.getAccountId())
                    .userId(account.getUser().getUserId())
                    .accountType(account.getAccountType())
                    .accountNumber(account.getAccountNumber())
                    .bankName(account.getBankName())
                    .accountName(account.getAccountName())
                    .balance(account.getBalance())
                    .build();
        }
    }
}