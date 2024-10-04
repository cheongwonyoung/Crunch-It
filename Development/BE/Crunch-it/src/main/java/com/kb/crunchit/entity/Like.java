package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class Like {
    public int likeId;
    public int boardId;
    public int writerId;
    public int likeUserId;
}
