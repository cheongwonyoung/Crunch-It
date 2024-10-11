package com.kb.crunchit.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Comment {
    public int commentId;
    public int boardId;
    public int writerId;
    public String content;
    public int likes;
    public LocalDateTime registerDate;
    public LocalDateTime modifyDate;
    public int userId;
    public List<Reply> replyList;

    public void addReplyList(List<Reply> replyList){
        this.replyList = replyList;
    }
}
