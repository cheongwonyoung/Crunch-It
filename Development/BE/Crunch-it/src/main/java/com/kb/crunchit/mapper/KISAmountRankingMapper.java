package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.StockMarketDataDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KISAmountRankingMapper {
    boolean exists(String stockName);

    void updateStock(StockMarketDataDTO.StockDataDTO stock);

    void insertStock(StockMarketDataDTO.StockDataDTO stock);
}
