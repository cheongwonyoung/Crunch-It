package com.kb.crunchit.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    public int user_id;
    public String nickname;
    public String password;
    public String pin_number;
    public String birth;
    public int gender;
    public String phone_number;
    public String email;
    public int md_user_id;
    public String profile_url;
    public int salary;

}
