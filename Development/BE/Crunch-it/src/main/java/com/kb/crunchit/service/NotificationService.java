package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.NotificationRequestDTO;
import com.kb.crunchit.entity.Notification;
import com.kb.crunchit.mapper.NotificationMapper;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private final NotificationMapper notificationMapper;

    //SSE용 emitter를 저장하는 맵
    private final ConcurrentHashMap<String, SseEmitter> emitters=new ConcurrentHashMap<>();

    //특정 사용자의 알림을 가져오는 메서드
    public List<Notification> getnotificationById(int userId){
        return notificationMapper.getNotificationById(userId);
    }

    //새로운 알림 추가
    public void insertNotification(NotificationRequestDTO notificationRequestDTO){
        notificationMapper.insertNotification(notificationRequestDTO);
        notifyUser(notificationRequestDTO.getUserId().toString(),notificationRequestDTO.getMessage());

    }

    //sse 구독 메서드
    public SseEmitter subscribeUser(String userId){
        SseEmitter emitter=new SseEmitter(0L); //타임아웃없이 설정
        emitters.put(userId,emitter);
        emitter.onCompletion(()->emitters.remove(userId));
        emitter.onTimeout(()->emitters.remove(userId));
        return emitter;
    }
    //특정 사용자에게 알림을 전송하는 메서드
    public void notifyUser(String userId,String message){
        SseEmitter emitter=emitters.get(userId);
        if(emitter!=null){
            try{
                emitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException | java.io.IOException e){
                emitters.remove(userId);
            }
        }
    }

}
