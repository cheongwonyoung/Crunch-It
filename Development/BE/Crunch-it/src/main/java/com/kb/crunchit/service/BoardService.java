package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public void createPost(BoardRequestDTO boardRequestDTO) {
        boardMapper.insertBoard(boardRequestDTO);

    }

    //특정 게시글 조회
    public BoardResponseDTO getPost(int boardId) {
        return boardMapper.selectBoard(boardId);
    }

    public List<BoardResponseDTO> getAllPosts() {
        return boardMapper.selectAllBoards();
    }


    public void updatePost(int boardId, BoardRequestDTO boardRequestDTO) {
        boardRequestDTO.setBoardId(boardId); //요청으로 받은 boardId를 DTO 객체에 설정
        boardMapper.modifyBoard(boardRequestDTO);
    }

    public void deletePost(int boardId) {
        boardMapper.deleteBoard(boardId);

    }
}
