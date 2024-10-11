package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.dto.response.analysis.BondResponseDto;
import com.kb.crunchit.entity.Bond;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BondMapper {

    int getUserIdByMdUserId(int mdUserId); // 사용자의 md_user_id를 이용해 메인 DB의 user_id를 조회

    boolean exists(@Param("bondCode") String bondCode, @Param("userId") int userId); // 특정 user_id에 대해 메인 DB에 존재하는 채권이 있는지 확인

    void updateBond(@Param("bond") BondResponseDto bond, @Param("userId") int userId); // 보유채권 정보 업데이트

    void insertBond(@Param("bond") BondResponseDto bond, @Param("userId") int userId); // 사용자가 보유한 새로운 채권 정보를 메인 DB에 삽입

    void deleteBond(@Param("bondCode") String bondCode, @Param("userId") int userId); // 사용자가 판매한 채권을 삭제

    List<String> getAllBondCodes(int userId); // 특정 user_id의 모든 채권 코드를 가져옴

    // 채권에 투자한 총 비용
    long calculateInvestAmount(int userId);

    // 사용자가 보유한 채권 코드를 반환
    List<String> getUserBonds(int userId);

    // 사용자가 보유한 채권 코드에 맞는 채권 평균 단가
    int getAveragePurchasePrice(@Param("userId") int user_id, @Param("bondCode") String bondCode);

    // 사용자가 보유한 채권 코드에 맞는 수량
    int getBondQuantity (@Param("userId") int user_id, @Param("bondCode") String bondCode);

    // 사용자가 보유한 채권 코드에 맞는 채권 이자율
    int getBondInterestRate (@Param("userId") int user_id, @Param("bondCode") String bondCode);
}
