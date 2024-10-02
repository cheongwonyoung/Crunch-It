package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> selectAllComments(int boardId);
    Comment selectCommentById(int commentId);
    void insertComment(CommentRequestDTO commentRequestDTO);
    void modifyComment(CommentRequestDTO commentRequestDTO);

    void deleteComment(int commentId);
}
