package com.kb.crunchit.mapper.recommendation.top;

import com.kb.crunchit.dto.response.recommendation.top.UserBondResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserBondMapper {
    // 채권 상위 3개 조회 메서드
    List<UserBondResponseDTO> findTop3BondsByUserId(int userId);
}
