package com.kb.crunchit.mapper;

import com.kb.crunchit.dto.response.UserFundResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserFundMapper {

    // 펀드 수익률이 가장 높은 user_id 조회
    Integer findTopProfitUserIdByType(@Param("type") String type);

    // 특정 user_id가 보유한 펀드 조회
    List<UserFundResponseDTO> selectFundsByUserId(@Param("userId") int userId);
}
