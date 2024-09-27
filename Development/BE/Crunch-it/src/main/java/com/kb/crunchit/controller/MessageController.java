package com.kb.crunchit.controller;

import com.kb.crunchit.dto.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chat/{roomId}")
    public Message sendMessage(@DestinationVariable Long roomId, Message message) {
        // 채팅 메시지를 브로드캐스트합니다.
        return message;
    }
}