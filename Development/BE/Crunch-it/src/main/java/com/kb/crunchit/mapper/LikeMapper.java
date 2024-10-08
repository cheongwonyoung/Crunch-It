package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.request.LikeRequestDTO;
import com.kb.crunchit.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {

    int getLikeById(int boardId);
    Like findLikeByBoardIdAndUserId(@Param("boardId") int boardId, @Param("userId") int userId);
    void addLike(LikeRequestDTO likeRequestDTO);
    void removeLike(@Param("boardId") int boardId, @Param("userId") int userId);

}
