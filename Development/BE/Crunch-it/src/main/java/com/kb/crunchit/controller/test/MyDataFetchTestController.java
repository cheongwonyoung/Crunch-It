package com.kb.crunchit.controller.test;

import com.kb.crunchit.mapper.UserMapper;
import com.kb.crunchit.mapper.analysis.*;
import com.kb.crunchit.service.MyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mydata")
@RequiredArgsConstructor
public class MyDataFetchTestController {

    private final MyDataService myDataService;
    private final UserMapper userMapper;
    private final AccountMapper accountMapper;
    private final StockMapper stockMapper;
    private final FundMapper fundMapper;
    private final BondMapper bondMapper;
    private final TransactionMapper transactionMapper;

    @PostMapping("/update/{mdUserId}")
    public ResponseEntity<String> updateAllData(@PathVariable int mdUserId) { // , Authentication auth
        // 이 마이데이터 업데이트를 사용자가 새로 고침하거나 하루 단위로 스케줄링 할 때 하면 될 것 같음..!
        // user 테이블에서 로그인한 사용자 이메일로 mdUserId 찾기
        // CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        // String email = customUserDetails.getUsername();

//        // 로그인한 사용자 정보 가져오기
//        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
//        Integer userID = user.getUserId();

        // 마이데이터 업데이트 테스트
//        myDataService.updateAccountData(mdUserId);
//        myDataService.updateTransactionData(mdUserId);
//        myDataService.updateStockData(mdUserId);
//        myDataService.updateFundData(mdUserId);
//        myDataService.updateBondData(mdUserId);

        int userId = userMapper.getUserIdByMdUserId(mdUserId);
        log.info("유저 ID : {}", userId); // OK

        long savingsAmount = accountMapper.calculateSavingsAmount(userId);
        log.info("예적금 통장 총 금액 : {}", savingsAmount); // OK

        long stockInvestAmount = stockMapper.calculateInvestAmount(userId);
        log.info("주식에 투자한 총 비용 : {}", stockInvestAmount); // OK

        List<String> userStocks = stockMapper.getUserStocks(userId);
        for (String stockCode : userStocks) {
            log.info("주식 코드 : {}", stockCode); // OK
        }
        long stockProfitAmount = myDataService.calculateStockProfitAmount(userId);
        log.info("주식 총 수익 : {}", stockProfitAmount); // OK

        long fundInvestAmount = fundMapper.calculateInvestAmount(userId);
        log.info("펀드에 투자한 총 비용 : {}", fundInvestAmount); // OK
        long fundProfitAmount = fundMapper.calculateProfitAmount(userId);
        log.info("펀드 총 수익 : {}", fundProfitAmount); // OK

        long bondInvestAmount = bondMapper.calculateInvestAmount(userId);
        log.info("채권에 투자한 총 비용 : {}", bondInvestAmount); // OK
        long bondProfitAmount = myDataService.calculateBondProfitAmount(userId);
        log.info("채권 총 수익 : {}", bondProfitAmount); // OK

        long totalAccountBalance = accountMapper.calculateTotalBalance(userId);
        log.info("현금자산 : {}", totalAccountBalance); // OK
        long totalOutcome = transactionMapper.calculateTotalOutcome(userId);
        log.info("총 지출 : {}", totalOutcome); // OK

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain", java.nio.charset.StandardCharsets.UTF_8));

        return new ResponseEntity<>("데이터 업데이트 완료", headers, HttpStatus.OK);
    }
}
