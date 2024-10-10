package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.DividendDataDTO;
import com.kb.crunchit.dto.response.StockMarketDataDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KISDividendMapper {
    boolean exists(String stockName);

    void updateStock(DividendDataDTO.StockDataDTO stock);

    void insertStock(DividendDataDTO.StockDataDTO stock);
}
