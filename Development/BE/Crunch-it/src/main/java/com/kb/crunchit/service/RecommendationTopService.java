package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.StockInfoRequestDto;
import com.kb.crunchit.dto.response.UserBondResponseDTO;
import com.kb.crunchit.dto.response.UserFundResponseDTO;
import com.kb.crunchit.dto.response.UserStockResponseDTO;
import com.kb.crunchit.dto.response.analysis.StockApiResponse;
import com.kb.crunchit.dto.response.analysis.StockInfoResponseDto;
import com.kb.crunchit.mapper.UserAssetStatisticsMapper;
import com.kb.crunchit.mapper.UserBondMapper;
import com.kb.crunchit.mapper.UserFundMapper;
import com.kb.crunchit.mapper.UserStockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RecommendationTopService {
    private final RedisService redisService;

    private final UserStockMapper userStockMapper;

    private final UserFundMapper userFundMapper;

    private final UserBondMapper userBondMapper;

    private final UserAssetStatisticsMapper userAssetStatisticsMapper; // user_asset_statistics 매퍼 추가

    @Value("${korea.stock.appkey}")
    private String appkey;

    @Value("${korea.stock.appsecret}")
    private String appsecret;

    @Value("${korea.stock.baseUri}")
    private String baseUri;

    @Value("${korea.stock.grant_type}")
    private String grant_type;

    // WebClient 생성 시 maxInMemorySize 설정 추가
    private final WebClient webClient = WebClient.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))  // 10MB로 설정
            .build();

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
                throw new IllegalArgumentException("Invalid type: " + type);
        }
        return Optional.ofNullable(topUserId);
    }

    // 주식 수익률이 가장 높은 사용자의 주식 중 상위 3개를 추천
    public List<UserStockResponseDTO> recommendTopStocksForUser(int userId) {
        List<UserStockResponseDTO> stocks = userStockMapper.findTop3StocksByUserId(userId);
        return stocks.stream()
                .filter(stock -> stock != null && stock.getAveragePurchasePrice() != null)
                .sorted((a, b) -> Long.compare(
                        b.getQuantity() * b.getAveragePurchasePrice(),
                        a.getQuantity() * a.getAveragePurchasePrice()))
                .limit(3)
                .toList();
    }

    // 펀드 수익률이 가장 높은 사용자의 펀드 중 상위 3개를 추천
    public List<UserFundResponseDTO> recommendTopFundsForUser(int userId) {
        List<UserFundResponseDTO> funds = userFundMapper.findTop3FundsByUserId(userId);
        return funds.stream()
                .filter(fund -> fund != null && fund.getInvestingAmount() != null)
                .sorted((a, b) -> Long.compare(
                        b.getInvestingAmount(),
                        a.getInvestingAmount()))
                .limit(3)
                .toList();
    }

    // 채권 수익률이 가장 높은 사용자의 채권 중 상위 3개를 추천
    public List<UserBondResponseDTO> recommendTopBondsForUser(int userId) {
        List<UserBondResponseDTO> bonds = userBondMapper.findTop3BondsByUserId(userId);
        return bonds.stream()
                .filter(bond -> bond != null && bond.getAveragePurchasePrice() != null)
                .sorted((a, b) -> Long.compare(
                        b.getQuantity() * b.getAveragePurchasePrice(),
                        a.getQuantity() * a.getAveragePurchasePrice()))
                .limit(3)
                .toList();
    }

    // 한국투자증권 토큰 발급
    public String getKoreaToken() {
        // Body 설정
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", grant_type);
        body.put("appkey", appkey);
        body.put("appsecret", appsecret);

        // POST 요청 보내기
        String responseToken = webClient.post()
                .uri(baseUri + "/oauth2/tokenP") // 실제 요청할 경로로 설정
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("응답: " + responseToken);

        redisService.saveKoreaToken("koreaToken", responseToken);

        return responseToken;
    }
}