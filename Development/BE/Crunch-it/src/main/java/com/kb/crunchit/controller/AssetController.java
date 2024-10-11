package com.kb.crunchit.controller;

import com.kb.crunchit.dto.response.analysis.AssetResponseDto;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/property")
@RequiredArgsConstructor
public class AssetController { // 사용자 자산 FE 전달

    private final AssetService assetService;

    @GetMapping("/statistics")
    public ResponseEntity<?> getAssetStatistics(Authentication auth, // 로그인 사용자 정보
                                                @RequestParam(required = false) Integer year, // 설정한 년도
                                                @RequestParam(required = false) Integer month) { // 설정한 월
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Integer userId = user.getUserId(); // 로그인한 사용자의 userId 가져오기

        Map<String, AssetResponseDto> result = new HashMap<>();
        result.put("currentMonth", assetService.getMonthStatistics(userId, year, month)); // 설정한 해당월
        result.put("previousMonth", assetService.getPreviousMonthStatistics(userId, year, month)); // 전달

        return ResponseEntity.ok(result);
    }
}
