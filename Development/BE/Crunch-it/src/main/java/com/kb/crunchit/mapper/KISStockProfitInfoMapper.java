package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.KISStockProfitResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KISStockProfitInfoMapper {
    boolean exists(String stockCode);

    void updateStock(KISStockProfitResponseDto.StockDataDTO stock);

    void insertStock(KISStockProfitResponseDto.StockDataDTO stock);
}
