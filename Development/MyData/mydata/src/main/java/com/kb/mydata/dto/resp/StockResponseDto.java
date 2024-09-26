package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.UserStock; // UserStock 엔티티를 임포트합니다.
import lombok.Builder;
import lombok.Data;

public class StockResponseDto {
    @Data
    @Builder
    public static class Info {
        private String stockCode;
        private int userId;
        private String stockName;
        private Integer averagePurchasePrice;
        private Integer quantity;
        private String stockMarket;

        public static Info fromEntity(UserStock userStock) {
            return Info.builder()
                    .stockCode(userStock.getStockCode())
                    .userId(userStock.getUser().getUserId())
                    .stockName(userStock.getStockName())
                    .averagePurchasePrice(userStock.getAveragePurchasePrice())
                    .quantity(userStock.getQuantity())
                    .stockMarket(userStock.getStockMarket())
                    .build();
        }
    }
}