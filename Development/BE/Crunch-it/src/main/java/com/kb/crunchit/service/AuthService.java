package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;

    public boolean checkEmail(String email){
        UserDto dto = userMapper.findByEmail(email);
        return dto != null;
    }

    public boolean checkNickname(String nickname){
        UserDto dto = userMapper.findByNickname(nickname);
        return dto != null;
    }

    public boolean singUp(UserDto dto){
        if(checkNickname(dto.getNickname())){
            return false;
        }
        if(checkEmail(dto.getEmail())){
            return false;
        }
        userMapper.insertUser(dto);
        return true;
    }

}
