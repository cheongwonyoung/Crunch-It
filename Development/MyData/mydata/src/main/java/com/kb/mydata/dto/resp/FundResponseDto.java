package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.UserFund;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

public class FundResponseDto {
    @Data
    @Builder
    public static class Info {
        private String fundCode;
        private int userId;
        private String fundType;
        private String fundName;
        private BigInteger evaluationAmount;
        private BigInteger investingAmount;

        public static Info fromEntity(UserFund userFund) {
            return Info.builder()
                    .fundCode(userFund.getFundCode())
                    .userId(userFund.getUser().getUserId())
                    .fundType(userFund.getFundType())
                    .fundName(userFund.getFundName())
                    .evaluationAmount(userFund.getEvaluationAmount())
                    .investingAmount(userFund.getInvestingAmount())
                    .build();
        }
    }
}