package com.kb.crunchit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.crunchit.dto.request.NotificationRequestDTO;
import com.kb.crunchit.entity.Notification;
import com.kb.crunchit.mapper.NotificationMapper;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public List<Notification> getnotificationById(Integer userId){
        return notificationMapper.getNotificationById(userId);
    }

    //새로운 알림 추가
    public void insertNotification(NotificationRequestDTO notificationRequestDTO){
        // 데이터베이스에 알림 저장
        notificationMapper.insertNotification(notificationRequestDTO);

        // SSE로 알림을 전송하기 위해 JSON 형식으로 직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonMessage = objectMapper.writeValueAsString(notificationRequestDTO);
            notifyUser(notificationRequestDTO.getUserId().toString(), jsonMessage);  // SSE로 JSON 데이터 전송
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //sse 구독 메서드
    public SseEmitter subscribeUser(String userId){
        //타임아웃 설정
        SseEmitter emitter=new SseEmitter(60L*1000*60); //만료 시간 1시간으로 설정
        emitters.put(userId,emitter);

        emitter.onCompletion(()->emitters.remove(userId));
        emitter.onTimeout(()->emitters.remove(userId));
        System.out.println("emitter creat, subscribe user");
        return emitter;
    }

    //특정 사용자에게 알림을 전송하는 메서드
    public void notifyUser(String userId, String message){
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
