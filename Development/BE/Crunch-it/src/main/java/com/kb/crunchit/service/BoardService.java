package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import java.util.*;

public interface BoardService {
    void createPost(BoardRequestDTO boardRequestDTO);
    //특정 게시글 조회
    BoardResponseDTO getPost(int boardId);
    //모든 게시글 조회
    List<BoardResponseDTO> getAllPosts();
    void updatePost(int boardId, BoardRequestDTO boardRequestDTO);
    void deletePost(int boardId);
}