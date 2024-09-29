package entity;

import lombok.Getter;

@Getter
public class Reply {
    public int reply_id;
    public int comment_id;
    public int user_id;
    public String content;
    public int likes;
    public String register_date;
    public String modify_date;
}
