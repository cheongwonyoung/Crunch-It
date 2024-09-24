package com.kb.crunchit.controller;

import com.kb.crunchit.dto.response.UserBondResponseDTO;
import com.kb.crunchit.dto.response.UserFundResponseDTO;
import com.kb.crunchit.dto.response.UserStockResponseDTO;
import com.kb.crunchit.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    // 주식 수익률이 가장 높은 사용자의 상위 3개 주식 추천
    @GetMapping("/top-stocks")
    public List<UserStockResponseDTO> recommendTopStocks() {
        Optional<Integer> userId = recommendationService.getTopProfitUserId("stock");
        if (userId.isPresent()) {
            return recommendationService.recommendTopStocksForUser(userId.get());
        } else {
            // 사용자를 찾지 못했을 경우 빈 리스트 반환
            return List.of();
        }
    }

    // 펀드 수익률이 가장 높은 사용자의 상위 3개 펀드 추천
    @GetMapping("/top-funds")
    public List<UserFundResponseDTO> recommendTopFunds() {
        Optional<Integer> userId = recommendationService.getTopProfitUserId("fund");
        if (userId.isPresent()) {
            return recommendationService.recommendTopFundsForUser(userId.get());
        } else {
            // 사용자를 찾지 못했을 경우 빈 리스트 반환
            return List.of();
        }
    }

    // 채권 수익률이 가장 높은 사용자의 상위 3개 채권 추천
    @GetMapping("/top-bonds")
    public List<UserBondResponseDTO> recommendTopBonds() {
        Optional<Integer> userId = recommendationService.getTopProfitUserId("bond");
        if (userId.isPresent()) {
            return recommendationService.recommendTopBondsForUser(userId.get());
        } else {
            // 사용자를 찾지 못했을 경우 빈 리스트 반환
            return List.of();
        }
    }
}