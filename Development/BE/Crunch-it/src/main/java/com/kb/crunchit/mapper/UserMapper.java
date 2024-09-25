package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto findByEmail(String email);
}
