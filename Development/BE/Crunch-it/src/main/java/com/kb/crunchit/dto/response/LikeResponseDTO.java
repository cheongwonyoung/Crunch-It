package com.kb.crunchit.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDTO {
    public int likeId;
    public int boardId;
    public int writerId;
    public int userId;
}
