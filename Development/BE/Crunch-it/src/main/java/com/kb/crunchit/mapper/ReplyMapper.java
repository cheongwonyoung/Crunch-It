package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.request.ReplyRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import com.kb.crunchit.entity.Reply;
import java.util.List;
@Mapper
public interface ReplyMapper {

    //각 댓글에 대한 모든 답글을 가져와서 service에서 comment Entity에 replyList설정
    List<Reply> selectAllReply();
    List<Reply> selectReplyById(int commentId);
    void insertReplyToReplyList(int commentId,Reply reply);

    void insertReply(ReplyRequestDTO ReplyRequestDTO);
    void modifyReply(ReplyRequestDTO ReplyRequestDTO);
    void deleteReply(int replyId);
}
