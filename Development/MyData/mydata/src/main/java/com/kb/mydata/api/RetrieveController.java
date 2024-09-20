package com.kb.mydata.api;

import com.kb.mydata.service.RetrieveService;
import com.kb.mydata.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/retrieve")
public class RetrieveController {
    private final RetrieveService retrieveService;

    @GetMapping("/account/{userId}")
    public ResponseEntity<?> accountList(@PathVariable("userId") Long userId){
        try{
            return new ResponseEntity<>(retrieveService.getAccountInfo(userId), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("계좌 조회 에러", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/transaction/{userId}")
    public ResponseEntity<?> transactionList(@PathVariable("userId") Long userId){
        try{
            return new ResponseEntity<>(retrieveService.getTransactionInfo(userId), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("입출금내역 조회 에러", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
