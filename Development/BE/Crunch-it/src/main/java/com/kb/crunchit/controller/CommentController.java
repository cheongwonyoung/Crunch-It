package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.service.CommentService;

import com.kb.crunchit.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community/{boardId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable int boardId){
        List<Comment> comments=commentService.getAllComments(boardId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createComment(@RequestBody CommentRequestDTO commentRequestDTO){
        commentService.createComment(commentRequestDTO);
        return ResponseEntity.ok("댓글 생성");
    }

    @PutMapping("/modify/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable int commentId,@RequestBody CommentRequestDTO commentRequestDTO){
        commentService.updateComment(commentId,commentRequestDTO);
        return ResponseEntity.ok("게시글 수정 성공");
    }

    @DeleteMapping("delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글 삭제 성공");
    }
}
