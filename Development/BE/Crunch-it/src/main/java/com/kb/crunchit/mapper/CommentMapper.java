package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.entity.Comment;

import java.util.List;

public interface CommentMapper {

    //댓글
    List<Comment> selectAllComments(int boardId);
    void insertComment(CommentRequestDTO commentRequestDTO);
    Comment selectLastInsertedComment();
    void modifyComment(int commentId,CommentRequestDTO commentRequestDTO);
    void deleteComment(int commentId);
}
