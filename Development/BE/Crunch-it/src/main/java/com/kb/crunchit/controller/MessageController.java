package com.kb.crunchit.controller;

import com.kb.crunchit.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MessageController {

    @MessageMapping("/chat/1")
    @SendTo("/topic/chat/1")
    public ChatMessage sendMessage(@DestinationVariable Long roomId, ChatMessage message) {
        // 채팅 메시지를 브로드캐스트합니다.
        System.out.println("@@@@@");
        log.info("!!!@@");
//        log.info("Received message for roomId: " + roomId + ", Content: " + message.getContent());

        return message;
    }

}