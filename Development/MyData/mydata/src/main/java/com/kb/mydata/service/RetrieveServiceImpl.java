package com.kb.mydata.service;

import com.kb.mydata.dto.resp.*;
import com.kb.mydata.entity.*;
import com.kb.mydata.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrieveServiceImpl implements RetrieveService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;
    private final FundRepository fundRepository;
    private final BondRepository bondRepository;

    @Override
    public List<AccountResponseDto.Info> getAccountInfo(Long userId) {

        List<AccountResponseDto.Info> res = new ArrayList<>();

        List<Account> accountList = accountRepository.findByUser_UserId(userId);

        for(Account account : accountList){
            AccountResponseDto.Info info = AccountResponseDto.Info.fromEntity(account);
            res.add(info);
        }

        return res;

    }

    @Override
    public List<IncomeOutcomeTransactionResponseDto.Info> getTransactionInfo(Long userId) {
        List<IncomeOutcomeTransactionResponseDto.Info> res = new ArrayList<>();

        List<IncomeOutcomeTransaction> transactionList = transactionRepository.findByUser_UserId(userId);

        for(IncomeOutcomeTransaction transaction : transactionList){
            IncomeOutcomeTransactionResponseDto.Info info = IncomeOutcomeTransactionResponseDto.Info.fromEntity(transaction);
            res.add(info);
        }

        return res;
    }

    @Override
    public List<StockResponseDto.Info> getStockInfo(Long userId) {
        List<StockResponseDto.Info> res = new ArrayList<>();

        List<UserStock> stockList = stockRepository.findByUser_UserId(userId);

        for(UserStock stock : stockList){
            StockResponseDto.Info info = StockResponseDto.Info.fromEntity(stock);
            res.add(info);
        }

        return res;
    }
    @Override
    public List<FundResponseDto.Info> getFundInfo(Long userId) {
        List<FundResponseDto.Info> res = new ArrayList<>();

        List<UserFund> fundList = fundRepository.findByUser_UserId(userId);

        for(UserFund fund : fundList){
            FundResponseDto.Info info = FundResponseDto.Info.fromEntity(fund);
            res.add(info);
        }

        return res;
    }
    @Override
    public List<BondResponseDto.Info> getBondInfo(Long userId) {
        List<BondResponseDto.Info> res = new ArrayList<>();

        List<UserBond> bondList = bondRepository.findByUser_UserId(userId);

        for(UserBond bond : bondList){
            BondResponseDto.Info info = BondResponseDto.Info.fromEntity(bond);
            res.add(info);
        }

        return res;
    }
}
