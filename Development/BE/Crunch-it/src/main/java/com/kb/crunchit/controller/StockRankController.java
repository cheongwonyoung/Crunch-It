package com.kb.crunchit.controller;

import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.StockRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
// 추후에 삭제 예정
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class StockRankController {
    private final StockRecommendService stockRecommendService;

    @GetMapping(value = "/profitrank", produces = "application/json")
    public ResponseEntity<?> profitrank(){
        return ResponseEntity.ok().body(stockRecommendService.getKisStockProfitRankData());
    }

    @GetMapping(value = "/amountrank", produces = "application/json")
    public ResponseEntity<?> amountrank(){
        return ResponseEntity.ok().body(stockRecommendService.getKisStockAmountRankData());
    }

    @GetMapping(value = "/dividendrank", produces = "application/json")
    public ResponseEntity<?> dividendrank(){
        return ResponseEntity.ok().body(stockRecommendService.getKisStockDividendRankData());
    }
}
