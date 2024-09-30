package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;

    public boolean checkEmail(String email){
        User user = userMapper.findByEmail(email);
        return user != null;
    }

    public boolean checkNickname(String nickname){
        User user = userMapper.findByNickname(nickname);
        return user != null;
    }

    public boolean singUp(User user){
        if(checkNickname(user.getNickname())){
            return false;
        }
        if(checkEmail(user.getEmail())){
            return false;
        }
        userMapper.insertUser(user);
        return true;
    }

}
