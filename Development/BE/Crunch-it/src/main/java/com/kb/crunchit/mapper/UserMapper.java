package com.kb.crunchit.mapper;

import com.kb.crunchit.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByEmail(String email);
    User findByNickname(String nickname);
    void insertUser(User dto);

    void updateUser(User user);
    void updateUserProfile(User user);
    String getUserProfile(String email);
    void updatePassword(User user);


    Integer onBoardingCheck(Integer userId);

    void updateUserOnBoarding(User user);

    // 모든 유저 조회
    List<User> getAllUsers();

    // mdUserId로 userId를 조회하는 메서드 추가
    int getUserIdByMdUserId(int mdUserId);
}
