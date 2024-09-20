package com.kb.mydata.service;



import com.kb.mydata.dto.resp.AccountResponseDto;
import com.kb.mydata.dto.resp.FundResponseDto;
import com.kb.mydata.dto.resp.IncomeOutcomeTransactionResponseDto;
import com.kb.mydata.dto.resp.StockResponseDto;

import java.util.List;

public interface RetrieveService {
    public List<AccountResponseDto.Info> getAccountInfo(Long userId);
    public List<IncomeOutcomeTransactionResponseDto.Info> getTransactionInfo(Long userId);
    public List<StockResponseDto.Info> getStockInfo(Long userId);
    public List<FundResponseDto.Info> getFundInfo(Long userId);

}
