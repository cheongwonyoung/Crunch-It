package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.*;
import com.kb.crunchit.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:properties/api.properties")
public class RecommendationTopService {

    private final UserStockMapper userStockMapper;

    private final UserFundMapper userFundMapper;

    private final UserBondMapper userBondMapper;

    private final UserAssetStatisticsMapper userAssetStatisticsMapper; // user_asset_statistics 매퍼 추가

    private final KISStockProfitInfoMapper kisStockProfitInfoMapper;

    private final KISAmountRankingMapper kisAmountRankingMapper;

    private final KISDividendMapper kisDividendMapper;

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
        KISAccessTokenResponseDTO responseToken = webClient.post()
                .uri("https://"+baseUri+":9443" + "/oauth2/tokenP") // 실제 요청할 경로로 설정
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(KISAccessTokenResponseDTO.class)
                .block();

        return responseToken.getAccessToken();
    }
    // 당기순이익
    public void getProfitAssetIndexRanking(String KISAccessToken) {
        KISStockProfitResponseDto result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .scheme("https")
                    .host(baseUri)
                    .port(9443)
                    .path("/uapi/domestic-stock/v1/ranking/profit-asset-index")
                    .queryParam("fid_cond_mrkt_div_code", "J")
                    .queryParam("fid_cond_scr_div_code", "20173")
                    .queryParam("fid_input_iscd", "0000")
                    .queryParam("fid_div_cls_code", "0")
                    .queryParam("fid_input_price_1", "") // 입력 가격1
                    .queryParam("fid_input_price_2", "") // 입력 가격2
                    .queryParam("fid_vol_cnt", "") // 거래량 수
                    .queryParam("fid_input_option_1", "2024")
                    .queryParam("fid_input_option_2", "0")
                    .queryParam("fid_rank_sort_cls_code", "3")
                    .queryParam("fid_blng_cls_code", "0")
                    .queryParam("fid_trgt_exls_cls_code", "0")
                    .queryParam("fid_trgt_cls_code", "0")
                    .build())
            .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
            .header(HttpHeaders.AUTHORIZATION, "Bearer "+KISAccessToken)//access_token
            .header("appkey", appkey)
            .header("appsecret", appsecret)
            .header("tr_id", "FHPST01730000")
            .header("custtype", "P")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(KISStockProfitResponseDto.class)
            .block();

        result.getOutput().forEach(stockData -> {
            if (kisStockProfitInfoMapper.exists(stockData.getIsinCdNm())) {
                kisStockProfitInfoMapper.updateStock(stockData);
            } else {
                kisStockProfitInfoMapper.insertStock(stockData);
            }
        });

    }
    // 시가총액상위
    public void getAmountRanking(String KISAccessToken) {
        StockMarketDataDTO result = webClient.get()
            .uri(uriBuilder -> uriBuilder
                    .scheme("https")
                    .host(baseUri)
                    .port(9443)
                    .path("/uapi/domestic-stock/v1/ranking/market-cap")
                    .queryParam("fid_cond_mrkt_div_code", "J")
                    .queryParam("fid_cond_scr_div_code", "20174")
                    .queryParam("fid_div_cls_code", "0")
                    .queryParam("fid_input_iscd", "0000")
                    .queryParam("fid_trgt_cls_code", "0")
                    .queryParam("fid_trgt_exls_cls_code", "0")
                    .queryParam("fid_input_price_1", "") // 입력 가격1
                    .queryParam("fid_input_price_2", "") // 입력 가격2
                    .queryParam("fid_vol_cnt", "") // 거래량 수
                    .build())
            .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
            .header(HttpHeaders.AUTHORIZATION, "Bearer "+KISAccessToken)//access_token
            .header("appkey", appkey)
            .header("appsecret", appsecret)
            .header("tr_id", "FHPST01740000")
            .header("custtype", "P")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(StockMarketDataDTO.class)
            .block();

        result.getOutput().forEach(stockData -> {
            if (kisAmountRankingMapper.exists(stockData.getStockName())) {
                kisAmountRankingMapper.updateStock(stockData);
            } else {
                kisAmountRankingMapper.insertStock(stockData);
            }
        });

    }

    // 배당률 상위
    public void getDividendRanking(String KISAccessToken) {
        DividendDataDTO result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(baseUri)
                        .port(9443)
                        .path("/uapi/domestic-stock/v1/ranking/dividend-rate")
                        .queryParam("CTS_AREA", "")
                        .queryParam("GB1", "0")
                        .queryParam("UPJONG", "0001")
                        .queryParam("GB2", "0")
                        .queryParam("GB3", "2")
                        .queryParam("F_DT", "20230101")
                        .queryParam("T_DT", "20240101")
                        .queryParam("GB4", "1")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+KISAccessToken)//access_token
                .header("appkey", appkey)
                .header("appsecret", appsecret)
                .header("tr_id", "HHKDB13470100")
                .header("custtype", "P")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DividendDataDTO.class)
                .block();

        result.getOutput().forEach(stockData -> {
            if (kisDividendMapper.exists(stockData.getIsinName())) {
                kisDividendMapper.updateStock(stockData);
            } else {
                kisDividendMapper.insertStock(stockData);
            }
        });

    }

    // 매일 오전 12시에 API 호출하여 DB 업데이트
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")  // 매일 00:00 실행
    public void fetchAndUpdateStockData() {
        String token = getKoreaToken();

        // 당기순이익 상위
        getProfitAssetIndexRanking(token);
        // 시가 총액 상위
        getAmountRanking(token);
        // 배당률 상위
        getDividendRanking(token);

    }

}