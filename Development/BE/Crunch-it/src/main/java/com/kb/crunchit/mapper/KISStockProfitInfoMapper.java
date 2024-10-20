package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.KISStockProfitResponseDto;
import com.kb.crunchit.entity.KisStockProfitRank;
import com.kb.crunchit.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KISStockProfitInfoMapper {
    boolean exists(String stockCode);

    void updateStock(KISStockProfitResponseDto.StockDataDTO stock);

    void insertStock(KISStockProfitResponseDto.StockDataDTO stock);

    List<KisStockProfitRank> getAllStokProfitRank();
}
