package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.NotificationRequestDTO;
import com.kb.crunchit.entity.Notification;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.JwtService;
import com.kb.crunchit.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //로그인한 사용자 알림 조회
    @GetMapping
    public List<Notification> getNotificationById(Authentication authentication){
        System.out.println("get Notification by userId");
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        Integer userId=user.getUserId();
        return notificationService.getnotificationById(userId);
    }

    //새로운 알림 추가
    @PostMapping("/insert")
    public ResponseEntity<String> insertNotification(@RequestBody NotificationRequestDTO notificationRequestDTO, Authentication authentication){
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        notificationRequestDTO.setUserId(user.getUserId());
        notificationService.insertNotification(notificationRequestDTO);
        return ResponseEntity.ok("insert notification");
    }

    //SSE 구독을 위한 엔드포인트
    @GetMapping("/subscribe")
    public SseEmitter subscribe(Authentication authentication,@RequestParam("token") String token){

        //JWT 토큰에서 유저 정보 추출
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        String userId=user.getUserId().toString();

        //SseEmitter 객체 생성 및 테스트 메시지 전송
        SseEmitter emitter=notificationService.subscribeUser(userId);
//        System.out.println("emitter******************************"+emitter);

        try{
            emitter.send(SseEmitter.event().name("test").data("Test notification"));
//            System.out.println("send emitter***********************");
        } catch (IOException e){
            e.printStackTrace();
            emitter.completeWithError(e);
        }
        return emitter;
    }
}
