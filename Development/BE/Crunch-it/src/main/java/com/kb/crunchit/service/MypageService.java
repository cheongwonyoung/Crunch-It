package com.kb.crunchit.service;

import com.kb.crunchit.dto.request.UserRequestDto;
import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserMapper userMapper;
    public UserDto getUserInfo(String email){
        User user = userMapper.findByEmail(email);
        return UserDto.builder()
                .user_id(user.getUser_id())
                .email(user.getEmail())
                .birth(user.getBirth())
                .phone_number(user.getPhone_number())
                .profile_url(user.getProfile_url())
                .build();
    }

    public void updateUser(UserRequestDto dto, String email){
        userMapper.updateUser(User.builder()
                        .email(email)
                        .nickname(dto.getNickname())
                .build());
    }
}
