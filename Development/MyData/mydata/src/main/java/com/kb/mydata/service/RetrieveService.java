package com.kb.mydata.service;



import com.kb.mydata.dto.resp.*;

import java.util.List;

public interface RetrieveService {
    public List<AccountResponseDto.Info> getAccountInfo(Long userId);
    public List<IncomeOutcomeTransactionResponseDto.Info> getTransactionInfo(Long userId);
    public List<StockResponseDto.Info> getStockInfo(Long userId);
    public List<FundResponseDto.Info> getFundInfo(Long userId);
    public List<BondResponseDto.Info> getBondInfo(Long userId);

}
