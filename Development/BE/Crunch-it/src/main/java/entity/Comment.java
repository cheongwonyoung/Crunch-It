package entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Comment {
    public int comment_id;
    public int board_id;
    public int user_id;
    public String content;
    public int likes;
    public String register_date;
    public String modify_date;

    public List<Reply> replyList;

    public void addReplyList(List<Reply> replyList){
        this.replyList = replyList;
    }
}
