// MessageDto.java
package com.kb.crunchit.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long roomId;
    private String sender;
    private String content;
    private String image; // 이미지 데이터를 위한 필드 (Base64 인코딩된 문자열)
}