package com.kb.crunchit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
// 추후에 삭제 예정

@RequestMapping("/auth")
public class TestController {
    @GetMapping("/login")
    public ResponseEntity<Map<String,Object>> login(){
        Map<String, Object> response = new HashMap<>();
        response.put("hello", "123");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String,Object>> test(){
        Map<String, Object> response = new HashMap<>();
        response.put("test", "123");
        return ResponseEntity.ok().body(response);
    }
}
