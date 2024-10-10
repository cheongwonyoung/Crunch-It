package com.kb.crunchit.service;

import com.kb.crunchit.entity.User;
import com.kb.crunchit.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Service
@RequiredArgsConstructor
public class OnBoardingService {
    private final UserMapper userMapper;

    public boolean onBoardingCheck(Integer userId){
        return (userMapper.onBoardingCheck(userId).equals(1));
    }

    public boolean updateOnBoarding(User user){
        try{
            userMapper.updateUserOnBoarding(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
