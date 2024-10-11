package com.kb.crunchit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 메시지 브로커 경로 설정
//        config.setApplicationDestinationPrefixes("/app"); // 클라이언트에서 보낼 때 사용할 경로 설정
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // WebSocket 엔드포인트 설정
                .setAllowedOrigins("http://localhost:3000") // CORS 설정
                .withSockJS(); // SockJS 지원 추가
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(128 * 1024); // 128KB로 메시지 크기 증가
        registration.setSendBufferSizeLimit(512 * 1024); // 512KB로 전송 버퍼 크기 증가
        registration.setSendTimeLimit(20 * 1000); // 20초 전송 제한 시간
    }
}