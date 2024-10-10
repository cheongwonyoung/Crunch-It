package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.dto.response.analysis.TransactionResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TransactionMapper {

    int getUserIdByMdUserId(int mdUserId); // 사용자의 md_user_id를 이용해 메인 DB의 user_id를 조회

    boolean exists(@Param("transactionId") int transactionId, @Param("userId") int userId); // 특정 user_id에 대해 메인 DB에 존재하는 거래내역이 있는지 확인

    void updateTransaction(@Param("transaction") TransactionResponseDto transaction, @Param("userId") int userId); // 거래내역 정보 업데이트

    void insertTransaction(@Param("transaction") TransactionResponseDto transaction, @Param("userId") int userId); // 새로운 거래내역을 메인 DB에 삽입

    void deleteTransaction(@Param("transactionId") int transactionId, @Param("userId") int userId); // 거래내역 삭제

    List<Integer> getAllTransactionIds(int userId); // 특정 user_id의 모든 거래내역 ID를 가져옴

    Integer getMonthlyOutcome(@Param("userId") Integer userId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
