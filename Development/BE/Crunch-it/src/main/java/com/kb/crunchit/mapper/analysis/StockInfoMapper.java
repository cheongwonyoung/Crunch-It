package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.entity.StockPriceInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockInfoMapper {
    boolean exists(String stockCode);

    void updateStock(StockPriceInfo stock);

    void insertStock(StockPriceInfo stock);
}
