package com.kb.crunchit.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Notification {
    private Integer notificationId;
    private Integer userId;
    private String nickname;
    private String title;
    private String message;
    private LocalDateTime createdAt;
}
