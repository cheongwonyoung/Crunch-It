package com.kb.crunchit.controller;

import com.kb.crunchit.entity.RecommendationApiDeposit;
import com.kb.crunchit.entity.RecommendationApiSaving;
import com.kb.crunchit.service.RecService;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecController {
    private final RecService recService;

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
}
