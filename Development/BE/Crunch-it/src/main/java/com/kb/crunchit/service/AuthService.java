package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserMapper userMapper;
    private final MailService mailService;
    private final RedisService redisService;

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

    public boolean changePassword(User user){
        try{
            userMapper.updatePassword(user);
            return true;
        }catch(Exception e){
            log.error(e.toString());
            return false;
        }
    }

    public void sendCodeToEmail(String toEmail){
        String title = "CrunchIT 인증번호";
        String authCode = this.createCode();
        mailService.sendEmail(toEmail, title, authCode);
        redisService.saveData(toEmail, authCode);
    }

    public boolean checkAuthCode(String email, String authCode){
        String redisAuthCode = redisService.getData(email);
        if(redisAuthCode.equals(authCode)){
            redisService.expireIfExists(email);
        }
        return redisAuthCode.equals(authCode);
    }

    private String createCode() {
        int length = 6;
        StringBuilder builder = new StringBuilder();
        try {
            Random random = SecureRandom.getInstanceStrong();

            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.toString());
        }
        return builder.toString();
    }

}
