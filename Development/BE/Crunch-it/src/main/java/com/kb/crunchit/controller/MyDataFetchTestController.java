package com.kb.crunchit.controller;

import com.kb.crunchit.service.MyDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/mydata")
public class MyDataFetchTestController {
    private final MyDataService myDataService;

    public MyDataFetchTestController(MyDataService myDataService) {
        this.myDataService = myDataService;
    }

    @PostMapping("/update/{mdUserId}")
    public ResponseEntity<String> updateAllData(@PathVariable int mdUserId) {
        // 이 마이데이터 업데이트를 사용자가 새로 고침하거나 하루 단위로 스케줄링 할 때 하면 될 것 같음..!
        myDataService.updateAccountData(mdUserId);
        myDataService.updateTransactionData(mdUserId);
        myDataService.updateStockData(mdUserId);
        myDataService.updateFundData(mdUserId);
        myDataService.updateBondData(mdUserId);

//        return ResponseEntity.ok("데이터 업데이트 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain", java.nio.charset.StandardCharsets.UTF_8));

        return new ResponseEntity<>("데이터 업데이트 완료", headers, HttpStatus.OK);
    }
}
