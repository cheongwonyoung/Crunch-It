package com.kb.mydata.service;

import com.kb.mydata.dto.resp.AccountResponseDto;
import com.kb.mydata.dto.resp.IncomeOutcomeTransactionResponseDto;
import com.kb.mydata.dto.resp.TestResponseDto;
import com.kb.mydata.entity.Account;
import com.kb.mydata.entity.IncomeOutcomeTransaction;
import com.kb.mydata.entity.Test;
import com.kb.mydata.repository.AccountRepository;
import com.kb.mydata.repository.TestRepository;
import com.kb.mydata.repository.TransactionRepository;
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
}
