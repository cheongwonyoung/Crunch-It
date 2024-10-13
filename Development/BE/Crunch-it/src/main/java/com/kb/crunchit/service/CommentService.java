package com.kb.crunchit.service;

import com.kb.crunchit.controller.NotificationController;
import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.dto.request.NotificationRequestDTO;
import com.kb.crunchit.mapper.CommentMapper;
import com.kb.crunchit.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final NotificationController notificationController;
    private final NotificationService notificationService;

    public Comment getCommentWithReply(int commentId){
        //댓글 조회
        Comment comment =commentMapper.selectCommentById(commentId);
        comment.addReplyList(comment.replyList);
        return comment;
    }

    public void createComment(CommentRequestDTO commentRequestDTO){
        commentMapper.insertComment(commentRequestDTO);  //댓글 추가 로직

        int boardId=commentRequestDTO.getBoardId();
        int boardWriterInt=commentMapper.getBoardWriter(boardId);
        String boardWriter=String.valueOf(boardWriterInt);


        //알림 생성
        NotificationRequestDTO notificationRequestDTO=new NotificationRequestDTO();
        notificationRequestDTO.setUserId(boardWriterInt);  //게시글 작성자
        notificationRequestDTO.setTitle("나의 게시판에 댓글을 남겼습니다.");
        notificationRequestDTO.setMessage(commentRequestDTO.getNickname()+"님이 게시글에 댓글을 남겼습니다.");
        notificationRequestDTO.setNickname(commentRequestDTO.getNickname());
        notificationService.insertNotification(notificationRequestDTO);
    }

    public List<Comment> getAllComments(int boardId){
        return commentMapper.selectAllComments(boardId);
    }

    public void updateComment(int commentId,CommentRequestDTO commentRequestDTO){
        commentRequestDTO.setCommentId(commentId);
        commentMapper.modifyComment(commentRequestDTO);
    }

    public void deleteComment(int commentId){
        commentMapper.deleteComment(commentId);
    }

}
