package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.RecommendationApiFundResponseDTO;
import com.kb.crunchit.entity.FundInfo;
import com.kb.crunchit.mapper.recommendation.api.RecommendationApiFundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationApiFundService {
    private final RecommendationApiFundMapper recommendationApiFundMapper;

    public List<RecommendationApiFundResponseDTO> getAllFunds() {
        List<FundInfo> funds = recommendationApiFundMapper.selectAllFunds();
        List<RecommendationApiFundResponseDTO> recommendationApiFundResponseDTOS =
                funds.stream()
                        .map(fund -> {
                            RecommendationApiFundResponseDTO dto = new RecommendationApiFundResponseDTO();
                            dto.fndTp = fund.getFndTp();
                            dto.fndNm = fund.getFndNm();
                            return dto;
                        })
                        .collect(Collectors.toList());

        return recommendationApiFundResponseDTOS;
    }
}
