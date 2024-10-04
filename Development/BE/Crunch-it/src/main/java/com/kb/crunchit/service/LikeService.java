package com.kb.crunchit.service;


import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.request.LikeRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.entity.Like;
import com.kb.crunchit.entity.Board;
import com.kb.crunchit.mapper.BoardMapper;
import com.kb.crunchit.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    public int getLikeById(int boardId){

//        int likeCount=likeMapper.getLikeById(boardId);
//        //like 수 board에도 반영하기
//        BoardResponseDTO boardResponseDTO=new BoardResponseDTO();
//        boardResponseDTO.setLikes(likeCount);
//        return likeCount;
        return likeMapper.getLikeById(boardId);
    }

    public void addLike(LikeRequestDTO likeRequestDTO){
        likeMapper.addLike(likeRequestDTO);
    }

    public void removeLike(int likeId){
        likeMapper.removeLike(likeId);
    }


}
