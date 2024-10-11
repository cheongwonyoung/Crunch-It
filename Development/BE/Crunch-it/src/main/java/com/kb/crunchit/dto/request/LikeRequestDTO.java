package com.kb.crunchit.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDTO {

    public int boardId;
    public int writerId;
    public int userId;
}
