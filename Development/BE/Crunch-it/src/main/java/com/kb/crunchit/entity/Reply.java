package com.kb.crunchit.entity;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Reply {
    public int replyId;
    public int commentId;
    public int writerId;
    public String content;
    public int likes;
    public LocalDateTime registerDate;
    public LocalDateTime modifyDate;
}
