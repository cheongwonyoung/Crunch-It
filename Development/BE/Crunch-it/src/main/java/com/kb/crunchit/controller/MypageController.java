package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.UserRequestDto;
import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    @GetMapping("/userInfo")
    ResponseEntity<Map<String, Object>> getUserInfo(Authentication auth){
        Map<String, Object> result = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        String email = customUserDetails.getUsername();
        UserDto userDto = mypageService.getUserInfo(email);
        result.put("code" , 200);
        result.put("data", userDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateUserInfo")
    ResponseEntity<Map<String,Object>> updateUserInfo(UserRequestDto dto, Authentication auth){
        Map<String,Object> result = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        String email = customUserDetails.getUsername();
        mypageService.updateUser(dto, email);
        result.put("code" , 200);
        result.put("message", "수정되었습니다");
        return ResponseEntity.ok(result);
    }
}
