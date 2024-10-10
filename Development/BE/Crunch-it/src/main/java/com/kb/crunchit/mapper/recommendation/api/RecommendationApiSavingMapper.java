package com.kb.crunchit.mapper.recommendation.api;

import com.kb.crunchit.entity.SavingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecommendationApiSavingMapper {

    // 적금 기본 데이터 삽입
    void insertSaving(SavingInfo saving);

    // 적금 기본 데이터 업데이트
    void updateSaving(SavingInfo saving);

    // 적금 기본 데이터 존재 여부 확인 (기본적으로 상품 코드만 확인)
    int existsSaving(@Param("productCode") String productCode);
}
