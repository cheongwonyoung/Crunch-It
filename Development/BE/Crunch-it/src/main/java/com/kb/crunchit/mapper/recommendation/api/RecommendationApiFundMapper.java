package com.kb.crunchit.mapper.recommendation.api;

import com.kb.crunchit.entity.FundInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendationApiFundMapper {
    List<FundInfo> selectAllFunds();
    void insertFund(FundInfo fund);
    void updateFund(FundInfo fund);
    int exists(String fndNm);
}
