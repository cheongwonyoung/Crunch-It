package com.kb.crunchit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final StockInfoService stockInfoService;
    private final MyDataService myDataService;

    // 매일 오전 12시에 업데이트
//    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Scheduled(cron = "0 15 01 * * *", zone = "Asia/Seoul")
    public void dailyUpdate() {
        log.info("이번달 자산정보 업데이트 시작 : {}", LocalDateTime.now());
        stockInfoService.fetchAndUpdateStockData(); // 주식 API 호출하여 DB 업데이트
        myDataService.scheduledDataUpdate(); // 모든 사용자의 마이데이터 및 이번달 자산 정보 업데이트
        log.info("이번달 자산정보 업데이트 끝 : {}", LocalDateTime.now());
    }

    // 매월 말일 자정에 모든 사용자의 월별 데이터를 이전
    // 마지막 업데이트 2분 후에 이전
    @Scheduled(cron = "0 2 0 L * *", zone = "Asia/Seoul")
    public void monthlyDataTransfer() {
        log.info("월별 데이터 이전 시작 : {}", LocalDateTime.now());
        myDataService.monthlyDataTransfer(); // 모든 사용자의 월별 데이터를 이전
        log.info("월별 데이터 이전 끝 : {}", LocalDateTime.now());
    }

}
