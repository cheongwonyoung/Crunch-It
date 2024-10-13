package com.kb.crunchit.dto.request;



import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardRequestDTO {

    private int boardId;
    private int writerId;
    private String nickname;
    private String title;
    private String content;
    private String category;


    // DTO to Entity



}