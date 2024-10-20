package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.entity.StockPriceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface StockInfoMapper {
    boolean exists(String stockCode); // 주식이 존재하는지 확인

    void updateStock(StockPriceInfo stock); // 주식 정보 업데이트

    void insertStock(StockPriceInfo stock); // 새 주식 정보 삽입

    // 특정 주식의 최근 종가를 조회, 전일 종가 조회
    Long getLastEndPrice(String stockCode);
}
