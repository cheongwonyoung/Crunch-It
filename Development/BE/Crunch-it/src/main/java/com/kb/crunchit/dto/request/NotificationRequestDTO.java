package com.kb.crunchit.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationRequestDTO {
    private Integer userId;
    private String title;
    private String message;
    private LocalDateTime createAt;
}
