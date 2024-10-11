package com.kb.crunchit.service;

import com.kb.crunchit.entity.User;
import com.kb.crunchit.mapper.recommendation.top.UserMapper;
import com.kb.crunchit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findByEmail(email);
        if(user == null){
            log.error("User not found with email");
            throw new UsernameNotFoundException("User not found with email : " + email);
        }

        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getUserId()
        );

    }
}
