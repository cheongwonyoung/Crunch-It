package com.kb.crunchit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationApiRequestDto {
    private String serviceKey;
    private String pageNo;
    private String resultType;
    private String beginBasDt;
    private String productType; // "deposit", "saving", "fund", "bond"
}
