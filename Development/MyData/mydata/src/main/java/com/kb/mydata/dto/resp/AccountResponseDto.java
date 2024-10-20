package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.UserAccount;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

public class AccountResponseDto {
    @Data
    @Builder
    public static class Info {
        private int accountId;
        private int userId;
        private String accountType;
        private String accountNumber;
        private String bankName;
        private String accountName;
        private BigInteger balance;

        public static Info fromEntity(UserAccount account) {
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