package com.kb.crunchit.mapper.recommendation.top;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAssetStatisticsMapper {

    // 주식 수익률이 가장 높은 user_id 조회
    Integer findTopUserIdByStockProfit();

    // 펀드 수익률이 가장 높은 user_id 조회
    Integer findTopUserIdByFundProfit();

    // 채권 수익률이 가장 높은 user_id 조회
    Integer findTopUserIdByBondProfit();
}
