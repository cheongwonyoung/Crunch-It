package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.RecommendationApiBondResponseDTO;
import com.kb.crunchit.entity.BondInfo;
import com.kb.crunchit.mapper.recommendation.api.RecommendationApiBondMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationApiBondService {
    private final RecommendationApiBondMapper recommendationApiBondMapper;

    public List<RecommendationApiBondResponseDTO> getAllBonds() {
        List<BondInfo> bonds = recommendationApiBondMapper.selectAllBonds();
        List<RecommendationApiBondResponseDTO> recommendationApiBondResponseDTOS =
                bonds.stream()
                        .map(bond -> {
                            RecommendationApiBondResponseDTO dto = new RecommendationApiBondResponseDTO();
                            dto.issuerName = bond.getIssuerName();
                            dto.productName = bond.getProductName();
                            dto.creditRating = bond.getCreditRating();
                            dto.surfaceRate = bond.getSurfaceRate();
                            dto.interestType = bond.getInterestType();
                            dto.maturityDate = bond.getMaturityDate();
                            dto.interestPaymentCycle = bond.getInterestPaymentCycle();
                            return dto;
                        })
                        .collect(Collectors.toList());

        return recommendationApiBondResponseDTOS;
    }
}
