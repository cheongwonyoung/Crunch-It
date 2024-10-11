package com.kb.crunchit.mapper.recommendation.api;

import com.kb.crunchit.entity.DepositInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendationApiDepositMapper {

    // 예금 데이터 삽입
    void insertDeposit(DepositInfo deposit);

    // 예금 데이터 업데이트
    void updateDeposit(DepositInfo deposit);

    // 예금 데이터 존재 여부 확인
    int exists(@Param("productCode") String productCode);
}

