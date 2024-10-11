package com.kb.crunchit.dto.response;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDTO {   //게시물 조회
    private int boardId;
    private int writerId;
    private String title;
    private String content;
    private String category;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private int likes;

}