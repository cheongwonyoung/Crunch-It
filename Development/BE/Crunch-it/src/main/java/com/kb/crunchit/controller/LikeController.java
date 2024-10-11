package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.LikeRequestDTO;
import com.kb.crunchit.dto.response.BoardResponseDTO;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.AuthService;
import com.kb.crunchit.service.BoardService;
import com.kb.crunchit.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.kb.crunchit.entity.Like;
import com.kb.crunchit.entity.Board;
import java.util.List;

@RestController
@RequestMapping("/apiClient/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<Boolean> toggleLike(@RequestBody LikeRequestDTO likeRequestDTO){
        boolean isLiked= likeService.toggleLike(likeRequestDTO);
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Integer> getLikeById(@PathVariable int boardId){
        Integer likeCount=likeService.getLikeById(boardId);
        return ResponseEntity.ok(likeCount);
    }

    //like수를 board의 likes 컬럼에도 반영 - entity는 setter가 없어서 DTO사용
//    @GetMapping("/board/{boardId}")
//    public ResponseEntity<BoardResponseDTO> setLikesToBoard(@PathVariable int boardId){
//        BoardResponseDTO boardResponseDTO=boardService.getBoardForLikes(boardId);
//        int likeCount= likeService.getLikeById(boardId);
//        boardResponseDTO.setLikes(likeCount);
//        return ResponseEntity.ok(boardResponseDTO);
//    }

    //로그인한 사용자 당 하나의 게시판에 한번씩
//    @PostMapping("/{boardId}")
//    public ResponseEntity<String> addLike(@RequestBody LikeRequestDTO likeRequestDTO){
//        likeService.addLike(likeRequestDTO);
//        return ResponseEntity.ok("success add like");
//    }
//
//    @DeleteMapping("/{boardId}/cancel")
//    public ResponseEntity<String> cancelLike(@PathVariable int boardId, Authentication authentication){
//        CustomUserDetails user=(CustomUserDetails) authentication.getPrincipal();
//        Integer userId=user.getUserId();
//        likeService.removeLike(boardId,userId);
//        return ResponseEntity.ok("success cancel like");
//    }

}
