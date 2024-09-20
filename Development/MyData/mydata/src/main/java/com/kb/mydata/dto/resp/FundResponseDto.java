package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.UserFund; // UserFund 엔티티를 임포트합니다.
import lombok.Builder;
import lombok.Data;

public class FundResponseDto {
    @Data
    @Builder
    public static class Info {
        private String fundCode;
        private String userName;              // MdUser의 user_name
        private String fundType;
        private String fundName;
        private Integer evaluationAmount;
        private Integer investingAmount;

        public static Info fromEntity(UserFund userFund) {
            return Info.builder()
                    .fundCode(userFund.getFundCode())
                    .userName(userFund.getUser().getUser_name()) // 사용자 이름
                    .fundType(userFund.getFundType())
                    .fundName(userFund.getFundName())
                    .evaluationAmount(userFund.getEvaluationAmount())
                    .investingAmount(userFund.getInvestingAmount())
                    .build();
        }
    }
}