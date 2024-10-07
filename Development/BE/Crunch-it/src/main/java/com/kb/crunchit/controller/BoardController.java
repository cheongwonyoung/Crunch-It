package com.kb.crunchit.controller;


import com.kb.crunchit.dto.request.BoardRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.entity.Board;
import com.kb.crunchit.entity.Comment;
import com.kb.crunchit.service.BoardService;
import com.kb.crunchit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody BoardRequestDTO boardRequestDTO){
        boardService.createPost(boardRequestDTO);


        return ResponseEntity.ok("게시글 생성 성공");
    }

    //특정 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<Board> getPost(@PathVariable int boardId){
        Board board= boardService.getPost(boardId);

        return ResponseEntity.ok(board);
    }

    @GetMapping
    public ResponseEntity<List<Board>> getAllPost(){
        List<Board> boardList=boardService.getAllPosts();
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

    //좋아요 수 count
//    @GetMapping("/likes/{boardId}")
//    public ResponseEntity<Integer> getBoardForLikes(@PathVariable int boardId){
//        Integer LikeCount= boardService.getBoardForLikes(boardId);
//        return ResponseEntity.ok(LikeCount);
//    }
}