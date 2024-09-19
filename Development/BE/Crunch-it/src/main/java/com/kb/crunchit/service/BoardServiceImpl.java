package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public void createPost(BoardRequestDTO boardRequestDTO) {
        boardMapper.insertBoard(boardRequestDTO);

    }

    //특정 게시글 조회
    @Override
    public BoardResponseDTO getPost(int boardId) {
        return boardMapper.selectBoard(boardId);
    }

    @Override
    public List<BoardResponseDTO> getAllPosts() {
        return boardMapper.selectAllBoards();
    }

    @Override
    public void updatePost(int boardId, BoardRequestDTO boardRequestDTO) {
        boardRequestDTO.setBoardId(boardId); //요청으로 받은 boardId를 DTO 객체에 설정
        boardMapper.modifyBoard(boardRequestDTO);
    }

    @Override
    public void deletePost(int boardId) {
        boardMapper.deleteBoard(boardId);

    }
}
