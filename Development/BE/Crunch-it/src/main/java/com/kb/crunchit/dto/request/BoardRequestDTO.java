package com.kb.crunchit.dto.request;


import entity.Board;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BoardRequestDTO {

    private int boardId;
    private int writerId;
    private String title;
    private String content;
    private String category;


    // DTO to Entity



}