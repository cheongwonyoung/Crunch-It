package com.kb.crunchit.controller;

import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.RecommendationApiBondService;
import com.kb.crunchit.service.RecommendationApiFundService;
import com.kb.crunchit.service.RecommendationTopService;
import com.kb.crunchit.service.StockRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.SecurityBuilder;
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
public class TestController {
    private final RecommendationTopService recommendationTopService;
    private final StockRecommendService stockRecommendService;
    private final RecommendationApiFundService recommendationApiFundService;
    private final RecommendationApiBondService recommendationApiBondService;

    @GetMapping("/test")
    public ResponseEntity<Map<String,Object>> test(Authentication auth){
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        String email = user.getUsername();
        String nickname = user.getNickname();
        Integer userId =  user.getUserId();
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("nickname", nickname);
        response.put("userId", userId);
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping("/koreaToken")
//    public ResponseEntity<?> token(){
////        return new ResponseEntity<>(recommendationTopService.getKoreaToken(), HttpStatus.valueOf(200));
////        return ResponseEntity.ok().body(recommendationTopService.getProfitAssetIndexRanking(recommendationTopService.getKoreaToken()));
////        return ResponseEntity.ok().body(recommendationTopService.getAmountRanking(recommendationTopService.getKoreaToken()));
//        return ResponseEntity.ok().body(recommendationTopService.getDividendRanking(recommendationTopService.getKoreaToken()));
//    }

    @GetMapping(value = "/fund", produces = "application/json")
    public ResponseEntity<?> fund(){
        return ResponseEntity.ok().body(recommendationApiFundService.getAllFunds());
    }

    @GetMapping(value = "/bond", produces = "application/json")
    public ResponseEntity<?> bond(){
        return ResponseEntity.ok().body(recommendationApiBondService.getAllBonds());
    }



}
