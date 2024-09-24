package com.kb.crunchit.dto.response;

import java.util.List;

public class RecommendationResponseDTO {
    private String type; // 상품 유형: stock, fund, bond
    private Integer userId; // 수익률이 가장 높은 사용자 ID
    private List<?> topItems; // 추천된 상위 3개의 상품 리스트

    public RecommendationResponseDTO(String type, Integer userId, List<?> topItems) {
        this.type = type;
        this.userId = userId;
        this.topItems = topItems;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<?> getTopItems() {
        return topItems;
    }

    public void setTopItems(List<?> topItems) {
        this.topItems = topItems;
    }
}
