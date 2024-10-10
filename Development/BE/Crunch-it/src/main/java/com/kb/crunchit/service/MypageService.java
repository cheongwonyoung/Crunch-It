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
                .nickname(user.getNickname())
                .user_id(user.getUserId())
                .email(user.getEmail())
                .birth(user.getBirth())
                .phone_number(user.getPhoneNumber())
                .profile_url(user.getProfileUrl())
                .build();
    }

    public void updateUser(UserRequestDto dto){
        userMapper.updateUser(User.builder()
                        .email(dto.getEmail())
                        .nickname(dto.getNickname())
                        .birth(dto.getBirth())
                        .phoneNumber(dto.getPhone_number())
                .build());
    }

    public String getUserProfile(String email){
        return userMapper.getUserProfile(email);
    }

    public void updateUserProfile(User user){
        userMapper.updateUserProfile(user);
    }
}
