package com.kb.crunchit.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
    private int boardId;
    private int writerId;
    private int commentId;
    private String content;
    private int likes;
    private LocalDateTime registerDate;  // 댓글 등록일
    private LocalDateTime modifyDate;    // 댓글 수정일
}