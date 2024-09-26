package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.UserFund; // UserFund 엔티티를 임포트합니다.
import lombok.Builder;
import lombok.Data;

public class FundResponseDto {
    @Data
    @Builder
    public static class Info {
        private String fundCode;
        private int userId;
        private String fundType;
        private String fundName;
        private Integer evaluationAmount;
        private Integer investingAmount;

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