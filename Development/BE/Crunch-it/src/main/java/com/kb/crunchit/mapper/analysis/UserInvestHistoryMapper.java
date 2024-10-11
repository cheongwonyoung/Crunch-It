package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.entity.InvestHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInvestHistoryMapper {
    void insertUserInvestHistory(InvestHistory history);
}
