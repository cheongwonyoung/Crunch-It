package com.kb.crunchit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final StockInfoService stockInfoService;
    private final MyDataService myDataService;
    private final RecommendationTopService recommendationTopService;
    private final RecommendationApiService recommendationApiService;

    //    @Scheduled(cron = "0 19 13 * * *", zone = "Asia/Seoul")
    // 매일 오전 12시에 업데이트
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void dailyUpdate() {
        String yesterday = LocalDate.now().minusDays(4).toString().replace("-", "");
        log.info("이번달 자산정보 업데이트 시작 : {}", LocalDateTime.now());
        stockInfoService.fetchAndUpdateStockData(yesterday); // 주식 API 호출하여 DB 업데이트, 잘 받아옴
        myDataService.scheduledDataUpdate(); // 모든 사용자의 마이데이터 및 이번달 자산 정보 업데이트
        log.info("이번달 자산정보 업데이트 끝 : {}", LocalDateTime.now());

        // KIS Token
        String token = recommendationTopService.getKoreaToken();
        // 당기순이익 상위
        recommendationTopService.getProfitAssetIndexRanking(token);
        // 시가 총액 상위
        recommendationTopService.getAmountRanking(token);
        // 배당률 상위
        recommendationTopService.getDividendRanking(token);

        // 예금, 적금, 펀드, 채권
        String beginBasDt = "20240101";
        recommendationApiService.fetchAndUpdateDepositData(beginBasDt);
        recommendationApiService.fetchAndUpdateSavingData(beginBasDt);
        recommendationApiService.fetchAndUpdateFundData(beginBasDt);
        recommendationApiService.fetchAndUpdateBondData(beginBasDt);

    }

    // 매월 말일 자정에 모든 사용자의 월별 데이터를 이전
    // 마지막 업데이트 10분 후에 이전
    @Scheduled(cron = "0 10 0 L * *", zone = "Asia/Seoul")
    public void monthlyDataTransfer() {
        log.info("월별 데이터 이전 시작 : {}", LocalDateTime.now());
        myDataService.monthlyDataTransfer(); // 모든 사용자의 월별 데이터를 이전
        log.info("월별 데이터 이전 끝 : {}", LocalDateTime.now());
    }




}
