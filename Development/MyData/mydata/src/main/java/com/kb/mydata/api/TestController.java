package com.kb.mydata.api;

import com.kb.mydata.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;

    @GetMapping("")
    public ResponseEntity<?> testList(){
        try{
            return new ResponseEntity<>(testService.getTestInfo(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("test 에러", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
