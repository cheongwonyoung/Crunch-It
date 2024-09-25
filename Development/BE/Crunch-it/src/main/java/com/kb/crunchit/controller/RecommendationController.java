package com.kb.crunchit.controller;

import com.kb.crunchit.dto.response.RecommendationResponseDTO;
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
    public RecommendationResponseDTO recommendTopStocks() {
        Optional<Integer> userId = recommendationService.getTopProfitUserId("stock");
        if (userId.isPresent()) {
            List<UserStockResponseDTO> topStocks = recommendationService.recommendTopStocksForUser(userId.get());
            return new RecommendationResponseDTO("주식", userId.get(), topStocks);
        } else {
            // 사용자를 찾지 못했을 경우 빈 결과 반환
            return new RecommendationResponseDTO("주식", null, List.of());
        }
    }

    // 펀드 수익률이 가장 높은 사용자의 상위 3개 펀드 추천
    @GetMapping("/top-funds")
    public RecommendationResponseDTO recommendTopFunds() {
        Optional<Integer> userId = recommendationService.getTopProfitUserId("fund");
        if (userId.isPresent()) {
            List<UserFundResponseDTO> topFunds = recommendationService.recommendTopFundsForUser(userId.get());
            return new RecommendationResponseDTO("펀드", userId.get(), topFunds);
        } else {
            // 사용자를 찾지 못했을 경우 빈 결과 반환
            return new RecommendationResponseDTO("펀드", null, List.of());
        }
    }

    // 채권 수익률이 가장 높은 사용자의 상위 3개 채권 추천
    @GetMapping("/top-bonds")
    public RecommendationResponseDTO recommendTopBonds() {
        Optional<Integer> userId = recommendationService.getTopProfitUserId("bond");
        if (userId.isPresent()) {
            List<UserBondResponseDTO> topBonds = recommendationService.recommendTopBondsForUser(userId.get());
            return new RecommendationResponseDTO("채권", userId.get(), topBonds);
        } else {
            // 사용자를 찾지 못했을 경우 빈 결과 반환
            return new RecommendationResponseDTO("채권", null, List.of());
        }
    }
}
