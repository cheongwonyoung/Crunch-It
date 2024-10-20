package com.kb.crunchit.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationResponseDTO {
    private Integer notificationId;
    private Integer userId;
    private String nickname;
    private String title;
    private String message;
    private LocalDateTime createdAt;
}
