package com.kb.crunchit.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private int boardId;
    private int writerId;
    private String nickname;
    private int commentId;
    private String content;
}