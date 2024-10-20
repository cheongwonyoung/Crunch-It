package com.kb.crunchit.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyResponseDTO {
    private int writerId;
    private int replyId;
    private int commentId;
    private String nickname;
    private String content;
    private int likes;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
}