package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.StockInfoRequestDto;
import com.kb.crunchit.dto.response.rank.AmountRankResponseDto;
import com.kb.crunchit.dto.response.rank.DividendRankResponseDto;
import com.kb.crunchit.dto.response.rank.ProfitRankResponseDto;
import com.kb.crunchit.entity.KisStockAmountRank;
import com.kb.crunchit.entity.KisStockDividendRank;
import com.kb.crunchit.entity.KisStockProfitRank;
import com.kb.crunchit.mapper.KISAmountRankingMapper;
import com.kb.crunchit.mapper.KISDividendMapper;
import com.kb.crunchit.mapper.KISStockProfitInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockRecommendService {
    private final KISStockProfitInfoMapper stockProfitInfoMapper;
    private final KISAmountRankingMapper amountRankingMapper;
    private final KISDividendMapper dividendMapper;

    // 수익 순위 조회
    public List<ProfitRankResponseDto> getKisStockProfitRankData() {

        List<KisStockProfitRank> allStokProfitRank = stockProfitInfoMapper.getAllStokProfitRank();
        List<ProfitRankResponseDto> profitRankResponseDtoList = allStokProfitRank.stream()
                .map(profitRank -> {
                    ProfitRankResponseDto dto = new ProfitRankResponseDto();
                    dto.kisStockProfitRankId = profitRank.kisStockProfitRankId;
                    dto.dataRank = profitRank.dataRank;
                    dto.isinCdNm = profitRank.isinCdNm;
                    dto.thtrNtin = profitRank.thtrNtin;
                    return dto;
                })
                .collect(Collectors.toList());

        return profitRankResponseDtoList;
    }

    // 시가총액 순위 조회
    public List<AmountRankResponseDto> getKisStockAmountRankData() {

        List<KisStockAmountRank> allStokAmountRank = amountRankingMapper.getAllStokAmountRank();
        List<AmountRankResponseDto> amountRankResponseDtoList = allStokAmountRank.stream()
                .map(amountRank -> {
                    AmountRankResponseDto dto = new AmountRankResponseDto();
                    dto.kisStockAmountRankId = amountRank.kisStockAmountRankId;
                    dto.dataRank = amountRank.dataRank;
                    dto.htsKorIsnm = amountRank.htsKorIsnm;
                    dto.mrktWholAvlsRlim = amountRank.mrktWholAvlsRlim;
                    return dto;
                })
                .collect(Collectors.toList());

        return amountRankResponseDtoList;
    }

    // 배당률 순위 조회
    public List<DividendRankResponseDto> getKisStockDividendRankData() {

        List<KisStockDividendRank> allStokDividendRank = dividendMapper.getAllStokDividendRank();
        List<DividendRankResponseDto> dividendRankResponseDtoList = allStokDividendRank.stream()
                .map(dividendRank -> {
                    DividendRankResponseDto dto = new DividendRankResponseDto();
                    dto.kisStockDividendRankId = dividendRank.kisStockDividendRankId;
                    dto.dataRank = dividendRank.dataRank;
                    dto.isinName = dividendRank.isinName;
                    dto.perStoDiviAmt = dividendRank.perStoDiviAmt;
                    return dto;
                })
                .collect(Collectors.toList());

        return dividendRankResponseDtoList;
    }
}
