package com.kb.crunchit.mapper.recommendation.api;

import com.kb.crunchit.entity.DepositOptionInfo;
import com.kb.crunchit.entity.RecommendationApiDepositOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendationApiDepositOptionMapper {

    // 예금 옵션 데이터 삽입
    void insertDepositOption(DepositOptionInfo depositOption);

    // 예금 옵션 데이터 업데이트
    void updateDepositOption(DepositOptionInfo depositOption);

    // 예금 옵션 데이터 존재 여부 확인
    int existsDepositOption(
            @Param("productCode") String productCode,
            @Param("rateTypeName") String rateTypeName,
            @Param("saveTerm") int saveTerm
    );

    RecommendationApiDepositOption getSixMonthDepositOption(String depositId);
    RecommendationApiDepositOption getYearDepositOption(String depositId);

}
