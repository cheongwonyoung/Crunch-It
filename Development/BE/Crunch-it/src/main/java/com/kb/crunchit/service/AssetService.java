package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.analysis.AssetResponseDto;
import com.kb.crunchit.mapper.analysis.AssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetMapper assetMapper;

    public AssetResponseDto getMonthStatistics(Integer userId, Integer year, Integer month) {

        // 만약 둘 중 하나라도 null 이면, 현재 날짜를 기준으로 연도와 월을 설정
        if (year == null || month == null) {
            LocalDate now = LocalDate.now();
            year = year != null ? year : now.getYear();
            month = month != null ? month : now.getMonthValue();
        }

        return assetMapper.getMonthStatistics(userId, year, month);
    }

    public AssetResponseDto getPreviousMonthStatistics(Integer userId, Integer year, Integer month) {

        // 만약 둘 중 하나라도 null 이면, 현재 날짜를 기준으로 연도와 월을 설정
        if (year == null || month == null) {
            LocalDate now = LocalDate.now();
            year = year != null ? year : now.getYear();
            month = month != null ? month : now.getMonthValue();
        }

        LocalDate previousMonth = LocalDate.of(year, month, 1).minusMonths(1);
        return assetMapper.getMonthStatistics(userId, previousMonth.getYear(), previousMonth.getMonthValue());
    }
}
