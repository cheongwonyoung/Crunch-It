package com.kb.crunchit.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
//@Builder
//@RequiredArgsConstructor
public class Board {
    public int boardId;
    public int writerId;
    public String title;
    public String content;
    public String category;
    public String nickname;
    public String profileUrl;

    public LocalDateTime registerDate;
    public LocalDateTime modifyDate;
    public int likes;

    public List<Comment> commentList;

    public void addCommentList(List<Comment> commentList){
        this.commentList = commentList;
    }

}
