package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kb.crunchit.entity.Board;
import com.kb.crunchit.entity.Comment;


import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public void createPost(BoardRequestDTO boardRequestDTO) {
        boardMapper.insertBoard(boardRequestDTO);
    }

    public Board getPost(int boardId) {
        return boardMapper.selectBoard(boardId);
    }

    public List<Board> getAllPosts() {
        return boardMapper.selectAllBoards();
    }

    public void updatePost(int boardId, BoardRequestDTO boardRequestDTO) {
        boardRequestDTO.setBoardId(boardId);
        boardMapper.modifyBoard(boardRequestDTO);
    }

    public void deletePost(int boardId) {
        boardMapper.deleteBoard(boardId);
    }

}
