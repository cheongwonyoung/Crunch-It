package com.kb.crunchit.service;

import com.kb.crunchit.controller.NotificationController;
import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.mapper.CommentMapper;
import com.kb.crunchit.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final NotificationController notificationController;

    public Comment getCommentWithReply(int commentId){
        //댓글 조회
        Comment comment =commentMapper.selectCommentById(commentId);
        comment.addReplyList(comment.replyList);
        return comment;
    }

    public void createComment(CommentRequestDTO commentRequestDTO){
        commentMapper.insertComment(commentRequestDTO);
        int boardId=commentRequestDTO.getBoardId();
        int boardWriterInt=commentMapper.getBoardWriter(boardId);
        String boardWriter=String.valueOf(boardWriterInt);
//        notificationController.notifyUser(boardWriter,"새로운 댓글이 달렸습니다.");
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
