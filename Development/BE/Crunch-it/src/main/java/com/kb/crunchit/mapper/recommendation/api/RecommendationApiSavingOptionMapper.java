package com.kb.crunchit.mapper.recommendation.api;

import com.kb.crunchit.entity.RecommendationApiDepositOption;
import com.kb.crunchit.entity.RecommendationApiSavingOption;
import com.kb.crunchit.entity.SavingOptionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecommendationApiSavingOptionMapper {

    // 적금 옵션 데이터 삽입
    void insertSavingOption(SavingOptionInfo savingOptionInfo);

    // 적금 옵션 데이터 업데이트
    void updateSavingOption(SavingOptionInfo savingOption);

    // 적금 옵션 데이터 존재 여부 확인
    int existsSavingOption(
            @Param("productCode") String productCode,
            @Param("reserveTypeName") String reserveTypeName,
            @Param("saveTerm") int saveTerm);

    List<RecommendationApiSavingOption> getSixMonthDepositOption(String depositId);
    List<RecommendationApiSavingOption> getYearDepositOption(String depositId);
}
