package com.kb.crunchit.mapper.recommendation.api;

import com.kb.crunchit.entity.FundInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecommendationApiFundMapper {
    void insertFund(FundInfo fund);
    void updateFund(FundInfo fund);
    int exists(String fndNm);
}
