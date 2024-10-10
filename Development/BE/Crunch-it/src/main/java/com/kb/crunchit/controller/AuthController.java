package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.EmailRequestDto;
import com.kb.crunchit.dto.request.UserRequestDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.service.AuthService;
import com.kb.crunchit.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/sendAuthCode")
    public ResponseEntity<Map<String, Object>> sendAuthCode(@RequestBody EmailRequestDto request){
        Map<String, Object> resultMap = new HashMap<>();
        boolean isForSignUp = request.isForSignUp();
        boolean isEmail = authService.checkEmail(request.getEmail());
        if(isEmail && isForSignUp){
            resultMap.put("code", 422);
            resultMap.put("message", "이미 존재하는 이메일입니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        if(!isEmail && !isForSignUp){
            resultMap.put("code", 422);
            resultMap.put("message", "존재하지 않는 이메일입니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        authService.sendCodeToEmail(request.getEmail());
         resultMap.put("code", 200);
         resultMap.put("message" , "인증코드를 보냈습니다.");
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/checkAuthCode")
    public ResponseEntity<Map<String, Object>> sendAuthCode(@RequestParam String email, @RequestParam String authCode){
        Map<String, Object> resultMap = new HashMap<>();
        if(!authService.checkAuthCode(email, authCode)){
            resultMap.put("code" , 422);
            resultMap.put("message", "인증번호가 틀립니다.");
            return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        }
        else{
            resultMap.put("code" , 200);
            resultMap.put("message" , "인증완료 되었습니다.");
        }
        return ResponseEntity.ok(resultMap);


    }

    @PostMapping("/changePassword")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody UserRequestDto dto){
        Map<String, Object> resultMap = new HashMap<>();
        String password = dto.getPassword();
        if(dto.getPassword() == null || dto.getEmail() == null){
            resultMap.put("code" , 500);
            resultMap.put("message", "제대로 입력해주세요");
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_ACCEPTABLE);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .build();

        if(authService.changePassword(user)) resultMap.put("code" , 200);
        else return new ResponseEntity<>(resultMap, HttpStatus.NOT_ACCEPTABLE);
        return ResponseEntity.ok(resultMap);
    }


    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserRequestDto dto){
        Map<String, Object> resultMap = new HashMap<>();
        String password = dto.getPassword();
        if(dto.getPassword() == null || dto.getEmail() == null || dto.getNickname() == null || dto.getPhone_number() == null || dto.getBirth() ==null){
            resultMap.put("code" , 500);
            resultMap.put("message", "제대로 입력해주세요");
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_ACCEPTABLE);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .nickname(dto.getNickname())
                .phoneNumber(dto.getPhone_number())
                .birth(dto.getBirth())
                .build();

        if(authService.singUp(user)) resultMap.put("code" , 200);
        else return new ResponseEntity<>(resultMap, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(resultMap);
    }
}
