package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    //댓글
    List<Comment> selectAllComments(int boardId);
    void insertComment(CommentRequestDTO commentRequestDTO);
    Comment selectLastInsertedComment();
    void modifyComment(CommentRequestDTO commentRequestDTO);

    void deleteComment(int commentId);
}
