package com.kb.crunchit.controller.test;

import com.kb.crunchit.service.StockInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockinfo")
@RequiredArgsConstructor
public class StockInfoTestController {

    private final StockInfoService stockInfoService;

    // 주식 시세 정보를 수동으로 업데이트하는 API 엔드포인트
    @GetMapping("/update")
    public ResponseEntity<String> updateStockData() {
        try {
            // StockInfoService 의 fetchAndUpdateStockData() 메서드를 호출하여 주식 데이터를 업데이트
            stockInfoService.fetchAndUpdateStockData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("text", "plain", java.nio.charset.StandardCharsets.UTF_8));
            return new ResponseEntity<>("데이터 업데이트 완료", headers, HttpStatus.OK);
        } catch (Exception e) {
            // 에러 발생 시, 오류 메시지 반환 및 로그 남기기
            e.printStackTrace();  // 콘솔 로그에 전체 스택 트레이스를 출력
            return ResponseEntity.status(500).body("주식시세정보 업데이트 중 오류 발생: " + e.getMessage());
        }
    }
}
