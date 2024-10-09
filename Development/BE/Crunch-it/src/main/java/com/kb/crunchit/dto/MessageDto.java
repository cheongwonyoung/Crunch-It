package com.kb.crunchit.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {
    private Long roomId;    // 채팅방 ID (프론트에서 전송)
    private String sender;  // 메시지 전송자 (프론트에서 전송)
    private String content; // 메시지 내용 (프론트에서 전송)

}