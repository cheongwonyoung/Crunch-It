package com.kb.crunchit.controller;

import com.kb.crunchit.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    //user당 emitter 객체 저장
    private final ConcurrentHashMap<String , SseEmitter> emitters=new ConcurrentHashMap<>();

    //Endpoint to register for notifications
    @GetMapping("/subscribe")
    public SseEmitter subscribe(Authentication authentication){
        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
        Integer userIdInt=user.getUserId();
        String userId = userIdInt.toString();

        SseEmitter emitter=new SseEmitter(0L);  //0 means no timeout
        emitters.put(userId,emitter);

        //clean up when done, clean up on timeout
        emitter.onCompletion(()->emitters.remove(userId));
        emitter.onTimeout(()->emitters.remove(userId));

        return emitter;
    }

    //notify a specific user
    public void notifyUser(String userId,String message){
        SseEmitter emitter=emitters.get(userId);
        if(emitter!=null){
            try{
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch(IOException e){
                emitters.remove(userId);
            }
        }
    }
}
