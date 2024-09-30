package com.kb.crunchit.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Board {
    public int board_id;
    public int writer_id;
    public String title;
    public String content;
    public String category;
    public String register_date;
    public String modify_date;
    public int likes;

    public List<Comment> commentList;

    public void addCommentList(List<Comment> commentList){
        this.commentList = commentList;
    }
}
