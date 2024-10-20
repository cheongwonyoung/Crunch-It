package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.StockMarketDataDTO;
import com.kb.crunchit.entity.KisStockAmountRank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KISAmountRankingMapper {
    boolean exists(String stockName);

    void updateStock(StockMarketDataDTO.StockDataDTO stock);

    void insertStock(StockMarketDataDTO.StockDataDTO stock);

    List<KisStockAmountRank> getAllStokAmountRank();
}
