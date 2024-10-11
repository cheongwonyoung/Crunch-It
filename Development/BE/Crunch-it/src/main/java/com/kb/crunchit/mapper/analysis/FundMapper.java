package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.dto.response.analysis.FundResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FundMapper {

    int getUserIdByMdUserId(int mdUserId); // 사용자의 md_user_id를 이용해 메인 DB의 user_id를 조회

    boolean exists(@Param("fundCode") String fundCode, @Param("userId") int userId); // 특정 user_id에 대해 메인 DB에 존재하는 펀드가 있는지 확인

    void updateFund(@Param("fund") FundResponseDto fund, @Param("userId") int userId); // 보유펀드 정보 업데이트

    void insertFund(@Param("fund") FundResponseDto fund, @Param("userId") int userId); // 사용자가 보유한 새로운 펀드 정보를 메인 DB에 삽입

    void deleteFund(@Param("fundCode") String fundCode, @Param("userId") int userId); // 사용자가 판 펀드를 삭제

    List<String> getAllFundCodes(int userId); // 특정 user_id의 모든 펀드 코드를 가져옴

    // 펀드에 투자한 총 비용
    long calculateInvestAmount(int userId);

    // 펀드 총 수익
    long calculateProfitAmount(int userId);
}
