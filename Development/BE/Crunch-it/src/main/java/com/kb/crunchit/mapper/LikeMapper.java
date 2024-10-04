package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.request.LikeRequestDTO;
import com.kb.crunchit.entity.Like;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeMapper {

    int getLikeById(int boardId);
    void addLike(LikeRequestDTO likeRequestDTO);
    void removeLike(int likeId);

}
