package com.kb.crunchit.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Board {
    public int boardId;
    public int writerId;
    public String title;
    public String content;
    public String category;
    public LocalDateTime registerDate;
    public LocalDateTime modifyDate;
    public int likes;

    public List<Comment> commentList;

    public void addCommentList(List<Comment> commentList){
        this.commentList = commentList;
    }
}
