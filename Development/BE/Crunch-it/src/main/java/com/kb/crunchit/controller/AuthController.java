package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.UserRequestDto;
import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;


    @GetMapping("/checkEmail")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email){
        Map<String, Object> resultMap = new HashMap<>();
        if(authService.checkEmail(email)){
            resultMap.put("code", 422);
            resultMap.put("message", "이미 존재하는 이메일입니다.");
        }else{
            resultMap.put("code", 200);
            resultMap.put("message", "사용 가능한 이메일입니다.");
        }

        return ResponseEntity.ok(resultMap);
    }
    @GetMapping("/checkNickname")
    public ResponseEntity<Map<String, Object>> checkNickname(@RequestParam String nickname){
        Map<String, Object> resultMap = new HashMap<>();
        if(authService.checkNickname(nickname)){
            resultMap.put("code", 422);
            resultMap.put("message", "이미 존재하는 닉네입입니다.");
        }else{
            resultMap.put("code", 200);
            resultMap.put("message", "사용 가능한 닉네임입니다.");
        }

        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserRequestDto dto){
        Map<String, Object> resultMap = new HashMap<>();
        String password = dto.getPassword();
        if(dto.getPassword() == null || dto.getEmail() == null || dto.getNickname() == null || dto.getPhone_number() == null){
            resultMap.put("code" , 500);
            resultMap.put("message", "제대로 입력해주세요");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .nickname(dto.getNickname())
                .build();

        if(authService.singUp(user)) resultMap.put("code" , 200);
        else resultMap.put("code" , 500);
        return ResponseEntity.ok(resultMap);
    }
}
