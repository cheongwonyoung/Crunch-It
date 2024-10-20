package com.kb.crunchit.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequestDTO {
    private int writerId;
    private int replyId;
    private String nickname;
    private int commentId;
    private String content;
}

