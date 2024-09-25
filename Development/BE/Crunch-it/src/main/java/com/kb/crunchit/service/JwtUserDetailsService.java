package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.mapper.UserMapper;
import com.kb.crunchit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = userMapper.findByEmail(email);
        if(userDto == null){
            throw new UsernameNotFoundException("User not found with email : " + email);
        }

        return new CustomUserDetails(
                userDto.getEmail(),
                userDto.getPassword()
        );

    }
}
