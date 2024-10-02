package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.ReplyRequestDTO;
import com.kb.crunchit.entity.Reply;
import com.kb.crunchit.entity.Comment;
import com.kb.crunchit.mapper.CommentMapper;
import com.kb.crunchit.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyMapper replyMapper;
    private final CommentMapper commentMapper;

    //ReplyRequestDTO => Reply
    public Reply convertDtoToEntity(ReplyRequestDTO replyRequestDTO){
        Reply reply =new Reply();
        reply.replyId=replyRequestDTO.getReplyId();
        reply.content=replyRequestDTO.getContent();
        reply.commentId=replyRequestDTO.getCommentId();
        reply.writerId=replyRequestDTO.getWriterId();
        return reply;
    }

    public List<Reply> getAllReply(int commentId){
        return replyMapper.selectAllReply(commentId);
    }

    public void createReply(int commentId,ReplyRequestDTO replyRequestDTO){
        //해당 comment 조회
        Comment comment =commentMapper.selectCommentById(commentId);
        //DB에 ReplyRequestDTO 삽입
        replyMapper.insertReply(replyRequestDTO);

        //받은 ReplyRequestDTO를 Reply객체로 변환
        Reply reply=convertDtoToEntity(replyRequestDTO);
        //comment 객체의 replyList 가져옴
        List<Reply> replyList=comment.getReplyList();

        if (replyList!=null){
            replyList.add(reply);  //기존 replyList 존재하면 그 리스트에 추가
        } else {
            replyList=new ArrayList<>();  // replyList 비어있는 경우 새로 생성해서 reply 추가
            replyList.add(reply);
        }

        //comment 객체에  replyList 저장
        comment.addReplyList(replyList);
    }


    public void modifyReply(int replyId,ReplyRequestDTO replyRequestDTO){
        replyRequestDTO.setReplyId(replyId);
        replyMapper.modifyReply(replyRequestDTO);
    }

    public void deleteReply(int ReplyId) {
        replyMapper.deleteReply(ReplyId);
    }
}
