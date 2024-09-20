package com.kb.mydata.service;



import com.kb.mydata.dto.resp.AccountResponseDto;
import com.kb.mydata.dto.resp.IncomeOutcomeTransactionResponseDto;

import java.util.List;

public interface RetrieveService {
    public List<AccountResponseDto.Info> getAccountInfo(Long userId);
    public List<IncomeOutcomeTransactionResponseDto.Info> getTransactionInfo(Long userId);
}
