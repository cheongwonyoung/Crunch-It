package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.dto.response.analysis.AccountResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper {

    // 사용자의 md_user_id를 이용해 메인 DB의 user_id를 조회
    int getUserIdByMdUserId(int mdUserId); // 마이데이터에서 받은 사용자 ID, 메인 DB 에서 조회된 user_id return

    // 특정 user_id에 대해 메인 DB에 존재하는 계좌가 있는지 확인
    boolean exists(@Param("accountId") int accountId, @Param("userId") int userId); // 마이데이터에서 받은 계좌 ID, 메인 DB의 사용자 ID
    // 계좌가 존재하면 true, 존재하지 않으면 false

    // 계좌 정보 업데이트
    void updateAccount(@Param("account") AccountResponseDto account, @Param("userId") int userId); // 마이데이터에서 받은 계좌 정보, 메인 DB의 사용자 ID

    // 새로운 계좌 정보를 메인 DB에 삽입
    void insertAccount(@Param("account") AccountResponseDto account, @Param("userId") int userId);

    // 계좌 정보를 삭제
    void deleteAccount(@Param("accountId") int accountId, @Param("userId") int userId); // 삭제할 계좌 ID

    // 특정 user_id의 모든 계좌 ID를 가져옴
    List<Integer> getAllAccountIds(int userId); // 메인 DB의 사용자 ID, 해당 사용자의 계좌 ID 목록 return

    // 예적금 통장 총 금액(정기예금, 적금)
    long calculateSavingsAmount(int userId);

    // 현금자산(보통예금, 증권)
    long calculateTotalBalance(int userId);
}
