package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.ReplyRequestDTO;
import com.kb.crunchit.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kb.crunchit.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyController
{

    @Autowired
    private ReplyService replyService;


    @PostMapping("/{commentId}")
    public ResponseEntity<String> createReply(@PathVariable int commentId, @RequestBody ReplyRequestDTO replyRequestDTO){
        replyService.createReply(commentId,replyRequestDTO);
        return ResponseEntity.ok("답글 생성 및 comment entity 객체에도 등록");
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<List<Reply>> getReplyByCommentId(@PathVariable int commentId){
        return ResponseEntity.ok(replyService.getReplyByCommentId(commentId));
    }

    @GetMapping
    public ResponseEntity<List<Reply>> getAllReply(){
        return ResponseEntity.ok(replyService.getAllReply());
    }

    @PutMapping("/modify/{replyId}")
    public ResponseEntity<String> modifyReply(@PathVariable int replyId,@RequestBody ReplyRequestDTO replyRequestDTO){
        replyService.modifyReply(replyId,replyRequestDTO);
        return ResponseEntity.ok("답글 수정 성공");
    }

    @DeleteMapping("/delete/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable int replyId){
        replyService.deleteReply(replyId);
        return ResponseEntity.ok("답글 삭제 성공");
    }








}
