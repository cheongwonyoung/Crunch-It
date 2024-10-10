package com.kb.crunchit.mapper.recommendation.top;

import com.kb.crunchit.dto.response.recommendation.top.UserFundResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFundMapper {
    // 펀드 상위 3개 조회 메서드
    List<UserFundResponseDTO> findTop3FundsByUserId(int userId);
}
