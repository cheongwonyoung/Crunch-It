package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.UserRequestDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.OnBoardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/onboarding")
public class OnBoardingController {
    private final OnBoardingService onBoardingService;
    @GetMapping("/check")
    ResponseEntity<Map<String, Object>> checkOnBoarding(Authentication auth){
        Map<String, Object> resultMap = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Integer userId = user.getUserId();
        try{
            resultMap.put("onBoarded" , onBoardingService.onBoardingCheck(userId));
        }
        catch(Exception e){
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(resultMap);
    }

    @PutMapping("/updateOnBoarding")
    ResponseEntity<Map<String, Object>> checkOnBoarding(@RequestBody UserRequestDto dto, Authentication auth) {
        Map<String, Object> resultMap = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
        Integer userId = user.getUserId();
        try {
            User userEntity = User.builder()
                    .userId(userId)
                    .mdUserId(1)
                    .outcomeLimit(dto.getOutcome_limit())
                    .salary(dto.getSalary())
                    .build();
            onBoardingService.updateOnBoarding(userEntity);
            resultMap.put("result" , "success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(resultMap);
    }
}
