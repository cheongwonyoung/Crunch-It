package com.kb.crunchit.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebsocketBrokerInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        final StompCommand commandType = headerAccessor.getCommand();

        if (StompCommand.CONNECT == commandType) {
            // 웹소켓 연결 요청 시 유저 인증
            System.out.println("1!!!!");
        } else if (StompCommand.SEND == commandType) {
            // SEND 시 메시지 내용 및 구독 경로 확인
            System.out.println("2!!!!");
            System.out.println("Message Payload: " + new String((byte[]) message.getPayload()));
            System.out.println("Destination: " + headerAccessor.getDestination());
        } else if (StompCommand.SUBSCRIBE == commandType) {
            System.out.println("3!!!!");
            System.out.println("Subscribe Destination: " + headerAccessor.getDestination());
        }
        return message;
    }
}