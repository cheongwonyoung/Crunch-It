package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.analysis.*;
import com.kb.crunchit.mapper.analysis.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class MyDataService { // WebClient 를 사용해 마이데이터 서버에 각 요청을 보내고, 응답을 DTO 리스트로 반환

    private final WebClient webClient;

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final StockMapper stockMapper;
    private final FundMapper fundMapper;
    private final BondMapper bondMapper;

    public MyDataService(WebClient.Builder webClientBuilder,
                         AccountMapper accountMapper,
                         TransactionMapper transactionMapper,
                         StockMapper stockMapper,
                         FundMapper fundMapper,
                         BondMapper bondMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); // 마이데이터 서버 기본 URL
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
        this.stockMapper = stockMapper;
        this.fundMapper = fundMapper;
        this.bondMapper = bondMapper;
    }

    // 1. 계좌 데이터 업데이트
    public void updateAccountData(int mdUserId) {
        List<AccountResponseDto> accountData = this.webClient.get()
                .uri("/api/retrieve/account/{mdUserId}", mdUserId)
                .retrieve()
                .bodyToFlux(AccountResponseDto.class)
                .collectList()
                .block();

        int userId = accountMapper.getUserIdByMdUserId(mdUserId);

        for (AccountResponseDto account : accountData) {
            if (accountMapper.exists(account.getAccountId(), userId)) {
                accountMapper.updateAccount(account, userId);
            } else {
                accountMapper.insertAccount(account, userId);
            }
        }

        List<Integer> existingAccounts = accountMapper.getAllAccountIds(userId);
        for (Integer accountId : existingAccounts) {
            if (accountData.stream().noneMatch(a -> a.getAccountId() == accountId)) {
                accountMapper.deleteAccount(accountId, userId);
            }
        }
    }

    // 2. 입출금 내역 데이터 업데이트
    public void updateTransactionData(int mdUserId) {
        List<TransactionResponseDto> transactionData = this.webClient.get()
                .uri("/api/retrieve/transaction/{mdUserId}", mdUserId)
                .retrieve()
                .bodyToFlux(TransactionResponseDto.class)
                .collectList()
                .block();

        int userId = transactionMapper.getUserIdByMdUserId(mdUserId);

        for (TransactionResponseDto transaction : transactionData) {
            if (transactionMapper.exists(transaction.getTransactionId(), userId)) {
                transactionMapper.updateTransaction(transaction, userId);
            } else {
                transactionMapper.insertTransaction(transaction, userId);
            }
        }

        List<Integer> existingTransactions = transactionMapper.getAllTransactionIds(userId);
        for (Integer transactionId : existingTransactions) {
            if (transactionData.stream().noneMatch(t -> t.getTransactionId() == transactionId)) {
                transactionMapper.deleteTransaction(transactionId, userId);
            }
        }
    }

    // 3. 주식 데이터 업데이트
    public void updateStockData(int mdUserId) {
        List<StockResponseDto> stockData = this.webClient.get()
                .uri("/api/retrieve/stock/{mdUserId}", mdUserId)
                .retrieve()
                .bodyToFlux(StockResponseDto.class)
                .collectList()
                .block();

        int userId = stockMapper.getUserIdByMdUserId(mdUserId);

        for (StockResponseDto stock : stockData) {
            if (stockMapper.exists(stock.getStockCode(), userId)) {
                stockMapper.updateStock(stock, userId);
            } else {
                stockMapper.insertStock(stock, userId);
            }
        }

        List<String> existingStocks = stockMapper.getAllStockCodes(userId);
        for (String stockCode : existingStocks) {
            if (stockData.stream().noneMatch(s -> s.getStockCode().equals(stockCode))) {
                stockMapper.deleteStock(stockCode, userId);
            }
        }
    }

    // 4. 펀드 데이터 업데이트
    public void updateFundData(int mdUserId) {
        List<FundResponseDto> fundData = this.webClient.get()
                .uri("/api/retrieve/fund/{mdUserId}", mdUserId)
                .retrieve()
                .bodyToFlux(FundResponseDto.class)
                .collectList()
                .block();

        int userId = fundMapper.getUserIdByMdUserId(mdUserId);

        for (FundResponseDto fund : fundData) {
            if (fundMapper.exists(fund.getFundCode(), userId)) {
                fundMapper.updateFund(fund, userId);
            } else {
                fundMapper.insertFund(fund, userId);
            }
        }

        List<String> existingFunds = fundMapper.getAllFundCodes(userId);
        for (String fundCode : existingFunds) {
            if (fundData.stream().noneMatch(f -> f.getFundCode().equals(fundCode))) {
                fundMapper.deleteFund(fundCode, userId);
            }
        }
    }

    // 5. 채권 데이터 업데이트
    public void updateBondData(int mdUserId) {
        List<BondResponseDto> bondData = this.webClient.get()
                .uri("/api/retrieve/bond/{mdUserId}", mdUserId)
                .retrieve()
                .bodyToFlux(BondResponseDto.class)
                .collectList()
                .block();

        int userId = bondMapper.getUserIdByMdUserId(mdUserId);

        for (BondResponseDto bond : bondData) {
            if (bondMapper.exists(bond.getBondCode(), userId)) {
                bondMapper.updateBond(bond, userId);
            } else {
                bondMapper.insertBond(bond, userId);
            }
        }

        List<String> existingBonds = bondMapper.getAllBondCodes(userId);
        for (String bondCode : existingBonds) {
            if (bondData.stream().noneMatch(b -> b.getBondCode().equals(bondCode))) {
                bondMapper.deleteBond(bondCode, userId);
            }
        }
    }
}
