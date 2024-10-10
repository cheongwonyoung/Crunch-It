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
//    @Autowired
//    private JwtService jwtService;

    //로그인한 사용자 알림 조회
    @GetMapping
    public List<Notification> getNotificationById(Authentication authentication){
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        Integer userId=user.getUserId();
        return notificationService.getnotificationById(userId);
    }

    //새로운 알림 추가
    @PostMapping
    public ResponseEntity<String> insertNotification(@RequestBody NotificationRequestDTO notificationRequestDTO, Authentication authentication){
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        notificationRequestDTO.setUserId(user.getUserId());
        notificationService.insertNotification(notificationRequestDTO);
        return ResponseEntity.ok("insert notification");
    }

    //SSE 구독을 위한 엔드포인트
    @GetMapping("/subscribe")
    public SseEmitter subscribe(Authentication authentication){

        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        String userId=user.getUserId().toString();
        return notificationService.subscribeUser(userId);
    }

//    //user당 emitter 객체 저장
//    private final ConcurrentHashMap<String , SseEmitter> emitters=new ConcurrentHashMap<>();
//
//    //Endpoint to register for notifications
//    @GetMapping("/subscribe")
//    public SseEmitter subscribe(Authentication authentication){
//        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
//        Integer userIdInt=user.getUserId();
//        String userId = userIdInt.toString();
//
//        SseEmitter emitter=new SseEmitter(0L);  //0 means no timeout
//        emitters.put(userId,emitter);
//
//        //clean up when done, clean up on timeout
//        emitter.onCompletion(()->emitters.remove(userId));
//        emitter.onTimeout(()->emitters.remove(userId));
//
//        return emitter;
//    }
//
//    //notify a specific user
//    public void notifyUser(String userId,String message){
//        SseEmitter emitter=emitters.get(userId);
//        if(emitter!=null){
//            try{
//                emitter.send(SseEmitter.event().name("message").data(message));
//            } catch(IOException e){
//                emitters.remove(userId);
//            }
//        }
//    }
}
