package com.kb.crunchit.service;

import com.kb.crunchit.dto.response.analysis.*;
import com.kb.crunchit.entity.*;
import com.kb.crunchit.mapper.UserAssetStatisticsMapper;
import com.kb.crunchit.mapper.UserMapper;
import com.kb.crunchit.mapper.analysis.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class MyDataService { // WebClient 를 사용해 마이데이터 서버에 각 요청을 보내고, 응답을 DTO 리스트로 반환

    private final WebClient webClient;

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;
    private final StockMapper stockMapper;
    private final FundMapper fundMapper;
    private final BondMapper bondMapper;
    private final UserMapper userMapper;
    private final StockInfoMapper stockInfoMapper;
    private final UserAssetStatisticsMapper userAssetStatisticsMapper;
    private final UserInvestHistoryMapper userInvestHistoryMapper;

    public MyDataService(WebClient.Builder webClientBuilder,
                         AccountMapper accountMapper,
                         TransactionMapper transactionMapper,
                         StockMapper stockMapper,
                         FundMapper fundMapper,
                         BondMapper bondMapper,
                         StockInfoMapper stockInfoMapper,
                         UserAssetStatisticsMapper userAssetStatisticsMapper,
                         UserInvestHistoryMapper userInvestHistoryMapper, UserMapper userMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build(); // 마이데이터 서버 기본 URL
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
        this.stockMapper = stockMapper;
        this.fundMapper = fundMapper;
        this.bondMapper = bondMapper;
        this.stockInfoMapper = stockInfoMapper;
        this.userAssetStatisticsMapper = userAssetStatisticsMapper;
        this.userInvestHistoryMapper = userInvestHistoryMapper;
        this.userMapper = userMapper;
    }

    // 모든 사용자의 마이데이터 및 이번달 자산 정보 업데이트
    public void scheduledDataUpdate() {
        List<User> allUsers = userMapper.getAllUsers();
        for (User user : allUsers) {
            updateUserData(user.getMdUserId());
        }
        log.info("모든 사용자의 데이터가 업데이트되었습니다: {}", LocalDateTime.now());
    }

    // 모든 사용자의 월별 데이터를 이전
    public void monthlyDataTransfer() {
        List<User> allUsers = userMapper.getAllUsers(); // 모든 사용자 정보 가져옴
        for (User user : allUsers) {
            transferMonthlyData(user.getMdUserId()); // 사용자의 마이데이터를 기준으로
        }
        log.info("모든 사용자의 월별 데이터가 이전되었습니다: {}", LocalDateTime.now());
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

    // 주식 수익을 계산, private
    public long calculateStockProfitAmount(int userId) {
        List<String> userStocks = stockMapper.getUserStocks(userId);
        long totalProfit = 0;
        for (String stockCode : userStocks) {
            long lastEndPrice = stockInfoMapper.getLastEndPrice(stockCode); // 전일 종가
            long investAmount = (long) stockMapper.getAveragePurchasePrice(userId, stockCode) * stockMapper.getStockQuantity(userId, stockCode); // 주식평균단가 * 수량
            long evaluationAmount = lastEndPrice * stockMapper.getStockQuantity(userId, stockCode); // 전일 종가 * 수량
            long profit = evaluationAmount - investAmount; // 해당 주식 수익 금액
            totalProfit += profit; // 총 주식 수익 금액
        }
        return totalProfit;
    }

    // 채권 수익을 계산, private
    public long calculateBondProfitAmount(int userId) {
        List<String> userBonds = bondMapper.getUserBonds(userId);
        long totalProfit = 0;
        for (String bondCode : userBonds) {
            long investAmount = (long) bondMapper.getAveragePurchasePrice(userId, bondCode) * bondMapper.getBondQuantity(userId, bondCode); // 평균 단가 * 채권 개수
            // 스케일링 사용, 정수로 계산하고 나중에 스케일을 조정 => 정밀도를 높이고 예기치 않은 반올림 오류를 줄일 수 있음
            long scaledInterestRate = bondMapper.getBondInterestRate(userId, bondCode) * 1_000_000L; // 백만 단위로 스케일링
            long profit = (investAmount * scaledInterestRate) / (365 * 1_000_000L);
//            double interestRate = (double) bondMapper.getBondInterestRate(userId, bondCode) / 365; // 채권이자율 / 365 => 일일이자율
//            long profit = (long) (investAmount * interestRate); // 해당 채권 총 수익
            totalProfit += profit; // 총 채권 수익 금액
        }
        return totalProfit;
    }

    // 사용자의 모든 금융 자산을 계산, private
    public AssetStatistics calculateAssetStatistics(int userId) {
        // 각 자산 유형별 금액 계산
        long savingsAmount = accountMapper.calculateSavingsAmount(userId); // 예적금 통장 총 금액
        long stockInvestAmount = stockMapper.calculateInvestAmount(userId); // 주식에 투자한 총 비용
        long stockProfitAmount = calculateStockProfitAmount(userId); // 주식 총 수익
        long fundInvestAmount = fundMapper.calculateInvestAmount(userId); // 펀드에 투자한 총 비용
        long fundProfitAmount = fundMapper.calculateProfitAmount(userId); // 펀드 총 수익
        long bondInvestAmount = bondMapper.calculateInvestAmount(userId); // 채권에 투자한 총 비용
        long bondProfitAmount = calculateBondProfitAmount(userId); // 채권 총 수익
        long totalAccountBalance = accountMapper.calculateTotalBalance(userId); // 현금자산
        long totalOutcome = transactionMapper.calculateTotalOutcome(userId); // 총 지출

        // 계산된 값들로 AssetStatistics 객체 생성 및 반환
        AssetStatistics statistics = new AssetStatistics();
        statistics.setUserId(userId);
        statistics.setSavingsAmount(savingsAmount);
        statistics.setStockInvestAmount(stockInvestAmount);
        statistics.setStockProfitAmount(stockProfitAmount);
        log.info("설정된 주식 총 수익: {}", statistics.getStockProfitAmount());
        statistics.setFundInvestAmount(fundInvestAmount);
        statistics.setFundProfitAmount(fundProfitAmount);
        log.info("설정된 펀드 총 수익: {}", statistics.getFundProfitAmount());
        statistics.setBondInvestAmount(bondInvestAmount);
        statistics.setBondProfitAmount(bondProfitAmount);
        statistics.setTotalAccountBalance(totalAccountBalance);
        statistics.setTotalOutcome(totalOutcome);

        return statistics;
    }

    // 사용자의 자산 통계를 계산하고 DB에 저장/업데이트
    private void updateAssetStatistics(int userId) {
        // 사용자의 모든 금융 자산을 계산함
        AssetStatistics statistics = calculateAssetStatistics(userId);
        if (userAssetStatisticsMapper.exists(userId)) { // 특정 사용자의 자산 통계 데이터가 존재하면
            userAssetStatisticsMapper.updateUserAssetStatistics(statistics); // 업데이트
        } else { // 존재하지 않으면
            userAssetStatisticsMapper.insertUserAssetStatistics(statistics); // 추가
        }
    }

    // 현재 자산 통계를 월별 자산 내역으로 이전
    private void transferMonthlyData(int mdUserId) {
        int userId = userMapper.getUserIdByMdUserId(mdUserId);
        AssetStatistics currentStats = userAssetStatisticsMapper.getUserAssetStatistics(userId);
        InvestHistory history = new InvestHistory();
        history.setUserId(userId);
        history.setSavingsAmount(currentStats.getSavingsAmount());
        history.setStockInvestAmount(currentStats.getStockInvestAmount());
        history.setStockProfitAmount(currentStats.getStockProfitAmount());
        history.setFundInvestAmount(currentStats.getFundInvestAmount());
        history.setFundProfitAmount(currentStats.getFundProfitAmount());
        history.setBondInvestAmount(currentStats.getBondInvestAmount());
        history.setBondProfitAmount(currentStats.getBondProfitAmount());
        history.setTotalAccountBalance(currentStats.getTotalAccountBalance());
        history.setTotalOutcome(currentStats.getTotalOutcome());

        // 생성일자
        LocalDateTime lastDayOfPreviousMonth = LocalDate.now().withDayOfMonth(1).minusDays(1).atTime(LocalTime.MIDNIGHT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = lastDayOfPreviousMonth.format(formatter);
        history.setRegisterDate(formattedDate);

        userInvestHistoryMapper.insertUserInvestHistory(history);
    }

    // 특정 사용자의 모든 금융 데이터를 업데이트
    public void updateUserData(int mdUserId) {
        // 사용자 마이데이터 관련 테이블 업데이트
        updateAccountData(mdUserId);
        updateTransactionData(mdUserId);
        updateStockData(mdUserId);
        updateFundData(mdUserId);
        updateBondData(mdUserId);

        // AssetStatistics 업데이트를 위해 userId 조회
        int userId = userMapper.getUserIdByMdUserId(mdUserId);
        updateAssetStatistics(userId);
    }
}
