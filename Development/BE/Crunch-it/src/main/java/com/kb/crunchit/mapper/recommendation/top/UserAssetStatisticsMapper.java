package com.kb.crunchit.mapper.recommendation.top;

import com.kb.crunchit.entity.AssetStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAssetStatisticsMapper { // 이번 달 자산 정보, 수익 정보

    // 주식 수익률이 가장 높은 user_id 조회
    Integer findTopUserIdByStockProfit();

    // 펀드 수익률이 가장 높은 user_id 조회
    Integer findTopUserIdByFundProfit();

    // 채권 수익률이 가장 높은 user_id 조회
    Integer findTopUserIdByBondProfit();

    // 특정 사용자의 자산 통계 데이터가 존재하는지 확인
    boolean exists(int userId);

    // 사용자의 자산 통계 정보를 업데이트
    void updateUserAssetStatistics(AssetStatistics statistics);

    // 새로운 사용자 자산 통계 정보를 삽입
    void insertUserAssetStatistics(AssetStatistics statistics);

    // 특정 사용자의 모든 자산 통계 정보를 조회
    AssetStatistics getUserAssetStatistics(int userId);
}
