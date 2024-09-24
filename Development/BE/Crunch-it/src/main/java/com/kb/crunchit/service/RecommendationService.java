package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.UserBondResponseDTO;
import com.kb.crunchit.dto.response.UserFundResponseDTO;
import com.kb.crunchit.dto.response.UserStockResponseDTO;
import com.kb.crunchit.mapper.UserAssetStatisticsMapper;
import com.kb.crunchit.mapper.UserBondMapper;
import com.kb.crunchit.mapper.UserFundMapper;
import com.kb.crunchit.mapper.UserStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private UserStockMapper userStockMapper;

    @Autowired
    private UserFundMapper userFundMapper;

    @Autowired
    private UserBondMapper userBondMapper;

    @Autowired
    private UserAssetStatisticsMapper userAssetStatisticsMapper;

    // 각 투자 상품에서 수익률이 가장 높은 user_id를 찾는 메서드
    public Optional<Integer> getTopProfitUserId(String type) {
        Integer topUserId = null;
        switch (type) {
            case "stock":
                topUserId = userAssetStatisticsMapper.findTopUserIdByStockProfit();
                break;
            case "fund":
                topUserId = userAssetStatisticsMapper.findTopUserIdByFundProfit();
                break;
            case "bond":
                topUserId = userAssetStatisticsMapper.findTopUserIdByBondProfit();
                break;
            default:
                throw new IllegalArgumentException("Invalid asset type: " + type);
        }
        return Optional.ofNullable(topUserId);
    }

    // 주식 수익률이 가장 높은 사용자의 주식 중 상위 3개를 추천
    public List<UserStockResponseDTO> recommendTopStocksForUser(int userId) {
        List<UserStockResponseDTO> stocks = userStockMapper.selectStocksByUserId(userId);
        return stocks.stream()
                .filter(stock -> stock != null && stock.getAveragePurchasePrice() != null && stock.getQuantity() != null)
                .sorted((a, b) -> Long.compare(
                        b.getQuantity() * b.getAveragePurchasePrice(),
                        a.getQuantity() * a.getAveragePurchasePrice()))
                .limit(3)
                .collect(Collectors.toList());
    }

    // 펀드 수익률이 가장 높은 사용자의 펀드 중 상위 3개를 추천
    public List<UserFundResponseDTO> recommendTopFundsForUser(int userId) {
        List<UserFundResponseDTO> funds = userFundMapper.selectFundsByUserId(userId);
        return funds.stream()
                .filter(fund -> fund != null && fund.getInvestingAmount() != null)
                .sorted((a, b) -> Long.compare(
                        b.getInvestingAmount(),
                        a.getInvestingAmount()))
                .limit(3)
                .collect(Collectors.toList());
    }

    // 채권 수익률이 가장 높은 사용자의 채권 중 상위 3개를 추천
    public List<UserBondResponseDTO> recommendTopBondsForUser(int userId) {
        List<UserBondResponseDTO> bonds = userBondMapper.selectBondsByUserId(userId);
        return bonds.stream()
                .filter(bond -> bond != null && bond.getAveragePurchasePrice() != null && bond.getQuantity() != null)
                .sorted((a, b) -> Long.compare(
                        b.getQuantity() * b.getAveragePurchasePrice(),
                        a.getQuantity() * a.getAveragePurchasePrice()))
                .limit(3)
                .collect(Collectors.toList());
    }
}
