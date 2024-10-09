package com.kb.crunchit.controller;

import com.kb.crunchit.dto.MessageDto;
import lombok.extern.slf4j.Slf4j; // Lombok의 SLF4J 로거
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j // 이 애노테이션으로 로거(log) 객체를 자동으로 생성
@Controller
public class MessageController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public MessageDto sendMessage(@DestinationVariable Long roomId, MessageDto message) {
        // SLF4J 로깅 사용
        log.info("Received message for roomId: " + roomId + ", Content: " + message.getContent());
        return message;
    }

}