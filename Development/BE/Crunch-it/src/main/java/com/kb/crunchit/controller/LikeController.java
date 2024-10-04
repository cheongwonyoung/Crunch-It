package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.LikeRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.service.BoardService;
import com.kb.crunchit.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kb.crunchit.entity.Like;
import com.kb.crunchit.entity.Board;
import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private BoardService boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity<Integer> getLikeById(@PathVariable int boardId){
        Integer likeCount=likeService.getLikeById(boardId);
        return ResponseEntity.ok(likeCount);
    }

    //like수를 board의 likes 컬럼에도 반영 - entity는 setter가 없어서 DTO사용
    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDTO> setLikesToBoard(@PathVariable int boardId){
        BoardResponseDTO boardResponseDTO=boardService.getBoardForLikes(boardId);
        int likeCount= likeService.getLikeById(boardId);
        boardResponseDTO.setLikes(likeCount);
        return ResponseEntity.ok(boardResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<String> addLike(@RequestBody LikeRequestDTO likeRequestDTO){
        likeService.addLike(likeRequestDTO);
        return ResponseEntity.ok("success add like");
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<String> cancelLike(@PathVariable int likeId){
        likeService.removeLike(likeId);
        return ResponseEntity.ok("success cancel like");
    }

}
