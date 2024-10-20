package com.kb.crunchit.controller;

import com.kb.crunchit.service.MyDataService;
import com.kb.crunchit.service.RecommendationApiService;
import com.kb.crunchit.service.RecommendationTopService;
import com.kb.crunchit.service.StockInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/productinfo")
@RequiredArgsConstructor
@Slf4j  // 로깅 기능 추가
public class RecommendationApiController {

    private final RecommendationApiService recommendationApiService;
    private final StockInfoService stockInfoService;
    private final MyDataService myDataService;
    private final RecommendationTopService recommendationTopService;

    // 예금 상품 데이터 업데이트
    @GetMapping("/update/deposit")
    public ResponseEntity<String> updateDepositData(
            @RequestParam(defaultValue = "20240101") String beginBasDt) {
        log.debug("예금 상품 데이터 업데이트 호출됨");

        try {
            recommendationApiService.fetchAndUpdateDepositData(beginBasDt);

            // 응답 Content-Type을 application/json으로 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.ok().headers(headers).body("{\"message\": \"예금 데이터 업데이트 완료\"}");
        } catch (Exception e) {
            log.error("예금 데이터 업데이트 중 오류 발생: {}", e.getMessage(), e);

            // 응답 Content-Type을 application/json으로 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                    .body("{\"error\": \"예금 데이터 업데이트 중 오류 발생: " + e.getMessage() + "\"}");
        }
    }

    // 적금 상품 데이터 업데이트
    @GetMapping("/update/saving")
    public ResponseEntity<String> updateSavingData(
            @RequestParam(defaultValue = "20240101") String beginBasDt) {
        log.debug("적금 상품 데이터 업데이트 호출됨");

        try {
            recommendationApiService.fetchAndUpdateSavingData(beginBasDt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.ok().headers(headers).body("{\"message\": \"적금 데이터 업데이트 완료\"}");
        } catch (Exception e) {
            log.error("적금 데이터 업데이트 중 오류 발생: {}", e.getMessage(), e);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                    .body("{\"error\": \"적금 데이터 업데이트 중 오류 발생: " + e.getMessage() + "\"}");
        }
    }

    // 펀드 상품 데이터 업데이트
    @GetMapping("/update/fund")
    public ResponseEntity<String> updateFundData(
            @RequestParam(defaultValue = "20240101") String beginBasDt) {
        log.debug("펀드 상품 데이터 업데이트 호출됨");

        try {
            recommendationApiService.fetchAndUpdateFundData(beginBasDt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.ok().headers(headers).body("{\"message\": \"펀드 데이터 업데이트 완료\"}");
        } catch (Exception e) {
            log.error("펀드 데이터 업데이트 중 오류 발생: {}", e.getMessage(), e);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                    .body("{\"error\": \"펀드 데이터 업데이트 중 오류 발생: " + e.getMessage() + "\"}");
        }
    }

    // 채권 상품 데이터 업데이트
    @GetMapping("/update/bond")
    public ResponseEntity<String> updateBondData(
            @RequestParam(defaultValue = "20240101") String beginBasDt) {
        log.debug("채권 상품 데이터 업데이트 호출됨");

        try {
            recommendationApiService.fetchAndUpdateBondData(beginBasDt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.ok().headers(headers).body("{\"message\": \"채권 데이터 업데이트 완료\"}");
        } catch (Exception e) {
            log.error("채권 데이터 업데이트 중 오류 발생: {}", e.getMessage(), e);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                    .body("{\"error\": \"채권 데이터 업데이트 중 오류 발생: " + e.getMessage() + "\"}");
        }
    }

    // 모든 상품 유형의 데이터 업데이트
    @GetMapping("/update/all")
    public ResponseEntity<String> updateAllProductData() {

        log.info("이번달 자산정보 업데이트 시작 : {}", LocalDateTime.now());
        stockInfoService.fetchAndUpdateStockData("20241008"); // 주식 API 호출하여 DB 업데이트, 잘 받아옴
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

        return ResponseEntity.ok().body("{\"message\": \"데이터 업데이트 완료\"}");

    }
}