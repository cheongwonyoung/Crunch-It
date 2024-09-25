package com.kb.crunchit.controller;


import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody BoardRequestDTO boardRequestDTO){
        boardService.createPost(boardRequestDTO);
        return ResponseEntity.ok("게시글 생성 성공");
    }

    //특정 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getPost(@PathVariable int boardId){
        BoardResponseDTO boardResponseDTO= boardService.getPost(boardId);
        return ResponseEntity.ok(boardResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllPost(){
        List<BoardResponseDTO> boardList=boardService.getAllPosts();
        return ResponseEntity.ok(boardList);
    }

    @PutMapping("/modify/{boardId}")
    public ResponseEntity<String> updatePost(@PathVariable int boardId,@RequestBody BoardRequestDTO boardRequestDTO){
        boardService.updatePost(boardId,boardRequestDTO);
        return ResponseEntity.ok("게시글 수정 성공");
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<String> deletePost(@PathVariable int boardId){
        boardService.deletePost(boardId);
        return ResponseEntity.ok("게시글 삭제 성공");
    }
}
