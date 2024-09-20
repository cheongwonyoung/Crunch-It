package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.UserBond; // UserBond 엔티티를 임포트합니다.
import lombok.Builder;
import lombok.Data;

public class BondResponseDto {
    @Data
    @Builder
    public static class Info {
        private String bondCode;
        private String userName;              // MdUser의 user_name
        private String bondName;
        private Integer averagePurchasePrice;
        private Integer quantity;
        private Integer interestRate;

        public static Info fromEntity(UserBond userBond) {
            return Info.builder()
                    .bondCode(userBond.getBondCode())
                    .userName(userBond.getUser().getUser_name()) // 사용자 이름
                    .bondName(userBond.getBondName())
                    .averagePurchasePrice(userBond.getAveragePurchasePrice())
                    .quantity(userBond.getQuantity())
                    .interestRate(userBond.getInterestRate())
                    .build();
        }
    }
}