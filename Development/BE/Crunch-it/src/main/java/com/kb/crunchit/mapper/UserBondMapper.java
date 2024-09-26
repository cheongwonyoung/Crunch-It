package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.UserBondResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserBondMapper {
    // 채권 상위 3개 조회 메서드
    List<UserBondResponseDTO> findTop3BondsByUserId(int userId);
}
