package com.kb.crunchit.service;


import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.request.LikeRequestDTO;
import com.kb.crunchit.dto.request.NotificationRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.entity.Like;
import com.kb.crunchit.entity.Board;
import com.kb.crunchit.mapper.BoardMapper;
import com.kb.crunchit.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    @Autowired
    private final LikeMapper likeMapper;
    private final NotificationService notificationService;

    public boolean toggleLike(LikeRequestDTO likeRequestDTO){
        Like existingLike=likeMapper.findLikeByBoardIdAndUserId(likeRequestDTO.getBoardId(),likeRequestDTO.getUserId());

        if(existingLike!=null){
            //이미 해당 보드에 좋아요가 존재하면 삭제 - 좋아요 취소
            likeMapper.removeLike(likeRequestDTO.getBoardId(),likeRequestDTO.getUserId());
            return false;
        } else {
            likeMapper.addLike(likeRequestDTO);

            //~님이 회원님의 글을 좋아합니다
            // 좋아요 누른 사람 usrId 사용해서 닉네임 가져와서 사용
            String nickname=likeMapper.findNicknameByUserId(likeRequestDTO.getUserId());

            NotificationRequestDTO notificationRequestDTO=new NotificationRequestDTO();
            notificationRequestDTO.setUserId(likeRequestDTO.getWriterId());
            notificationRequestDTO.setNickname(nickname);
            notificationRequestDTO.setTitle("회원님의 글을 좋아합니다!");
            notificationRequestDTO.setMessage("");

            notificationService.insertNotification(notificationRequestDTO);  // 알림 저장
            return true;
        }
    }


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

    public void removeLike(int boardId, int userId){
        likeMapper.removeLike(boardId,userId);
    }



}
