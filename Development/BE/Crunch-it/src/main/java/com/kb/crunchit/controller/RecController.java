package com.kb.crunchit.controller;

import com.kb.crunchit.entity.RecommendationApiDeposit;
import com.kb.crunchit.entity.RecommendationApiSaving;
import com.kb.crunchit.service.RecService;
import com.kb.crunchit.service.RecommendationApiBondService;
import com.kb.crunchit.service.RecommendationApiFundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecController {
    private final RecService recService;
    private final RecommendationApiFundService recommendationApiFundService;
    private final RecommendationApiBondService recommendationApiBondService;

    @GetMapping("/deposit")
    ResponseEntity<?> getAllRecommendationDeposit(){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            List<RecommendationApiDeposit> depositList = recService.getAllRecommendationDeposit();
            recService.setRecommendationApiDepositList(depositList);
            resultMap.put("depositList", depositList);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
        }
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/saving")
    ResponseEntity<?> getAllRecommendationSaving(){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            List<RecommendationApiSaving> savingList = recService.getAllRecommendationSaving();
            recService.setRecommendationApiSavingList(savingList);
            resultMap.put("savingList", savingList);
        }catch(Exception e){
            log.error(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
        }
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping(value = "/fund", produces = "application/json")
    public ResponseEntity<?> fund(){
        return ResponseEntity.ok().body(recommendationApiFundService.getAllFunds());
    }

    @GetMapping(value = "/bond", produces = "application/json")
    public ResponseEntity<?> bond(){
        return ResponseEntity.ok().body(recommendationApiBondService.getAllBonds());
    }

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllRecommendations() {
//        Map<String, Object> resultMap = new HashMap<>();
//
//        try {
//            // 비동기 처리로 각 데이터를 병렬로 가져옴
//            CompletableFuture<List<RecommendationApiDeposit>> depositFuture = CompletableFuture.supplyAsync(() -> recService.getAllRecommendationDeposit());
//            CompletableFuture<List<RecommendationApiSaving>> savingFuture = CompletableFuture.supplyAsync(() -> recService.getAllRecommendationSaving());
//            CompletableFuture<?> fundFuture = CompletableFuture.supplyAsync(() -> recommendationApiFundService.getAllFunds());
//            CompletableFuture<?> bondFuture = CompletableFuture.supplyAsync(() -> recommendationApiBondService.getAllBonds());
//
//            // 모든 Future가 완료될 때까지 기다림
//            CompletableFuture.allOf(depositFuture, savingFuture, fundFuture, bondFuture).join();
//
//            // 결과 맵에 각 데이터를 저장
//            resultMap.put("depositList", depositFuture.get());
//            resultMap.put("savingList", savingFuture.get());
//            resultMap.put("fundList", fundFuture.get());
//            resultMap.put("bondList", bondFuture.get());
//        } catch (Exception e) {
//            log.error("Error in fetching recommendations", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
//        }
//        return ResponseEntity.ok(resultMap);
//    }
//
}
