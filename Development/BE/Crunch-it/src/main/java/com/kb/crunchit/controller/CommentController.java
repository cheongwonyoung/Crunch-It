package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.CommentRequestDTO;
import com.kb.crunchit.service.CommentService;

import com.kb.crunchit.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable int boardId){
        List<Comment> comments=commentService.getAllComments(boardId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<Comment> getCommentWithReply(@PathVariable int commentId){
        Comment comment= commentService.getCommentWithReply(commentId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/create/{boardId}")
    public ResponseEntity<String> createComment(@PathVariable int boardId,@RequestBody CommentRequestDTO commentRequestDTO){
        commentRequestDTO.setBoardId(boardId);
        commentService.createComment(commentRequestDTO);
        return ResponseEntity.ok("댓글 생성 성공");
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
