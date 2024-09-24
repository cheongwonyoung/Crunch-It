package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.UserFundResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFundMapper {
    // 펀드 상위 3개 조회 메서드
    List<UserFundResponseDTO> findTop3FundsByUserId(int userId);
}
