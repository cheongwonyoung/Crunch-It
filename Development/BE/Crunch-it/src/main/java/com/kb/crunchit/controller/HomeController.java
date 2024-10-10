package com.kb.crunchit.controller;

import com.kb.crunchit.entity.Octopus;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/getUserOctopus")
    public ResponseEntity<Map<String, Object>> getUserOctopus(Authentication auth){
        Map<String, Object> resultMap = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Integer userId =  user.getUserId();
        try{
            Octopus userOctopus = homeService.getUserOctopus(userId);
            resultMap.put("userOctopus", userOctopus);
            return ResponseEntity.ok(resultMap);
        }catch(Exception e){
            return new ResponseEntity<>(resultMap,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMonthlyOutcome")
    public ResponseEntity<Map<String, Object>> getMonthlyOutcome(Authentication auth){
        Map<String, Object> resultMap = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Integer userId =  user.getUserId();
        try{
            Integer total_monthly_outcome = homeService.getMonthlyOutcome(userId);
            resultMap.put("total_monthly_outcome", total_monthly_outcome);
        }
        catch(Exception e){
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(resultMap);
    }
}
