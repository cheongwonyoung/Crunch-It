package com.kb.crunchit.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    public Integer user_id;
    public String nickname;
    public String password;
    public String pin_number;
    public String birth;
    public Integer gender;
    public String phone_number;
    public String email;
    public Integer md_user_id;
    public String profile_url;
    public Integer salary;
}
