package com.kb.crunchit.mapper.recommendation.api;

import com.kb.crunchit.entity.SavingOptionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
