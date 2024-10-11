package com.kb.crunchit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.crunchit.dto.request.StockInfoRequestDto;
import com.kb.crunchit.dto.response.analysis.StockApiResponse;
import com.kb.crunchit.dto.response.analysis.StockInfoResponseDto;
import com.kb.crunchit.entity.StockPriceInfo;
import com.kb.crunchit.mapper.analysis.StockInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@PropertySource("classpath:properties/api.properties")
@Slf4j
@RequiredArgsConstructor
public class StockInfoService { // API 호출을 보내고, 응답을 받아서 DB에 저장

    // WebClient 생성 시 maxInMemorySize 설정 추가
    private final WebClient webClient = WebClient.builder()
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))  // 10MB로 설정
            .build();

    private final StockInfoMapper stockInfoMapper;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 매퍼 추가

    @Value("${stock.api.serviceKey}")
    private String serviceKey;

    @Value("${stock.api.baseUri}")
    private String baseUri;

    // 주식 API 호출하여 DB 업데이트
    public void fetchAndUpdateStockData() {
        String yesterday = LocalDate.now().minusDays(1).toString().replace("-", "");
//        String fixedDate = "20241008"; // 테스트용 고정 날짜

        for (int pageNo = 1; pageNo <= 3; pageNo++) {
            StockInfoRequestDto requestDto = new StockInfoRequestDto(serviceKey, "1000", String.valueOf(pageNo), "json", yesterday);
            fetchStockData(requestDto);
        }
    }

    // API 에서 데이터 받아오기
    private void fetchStockData(StockInfoRequestDto requestDto) {
        try {
            // URI 클래스 사용하여 URI 생성
            URI uri = new URI(UriComponentsBuilder.fromUriString(baseUri)
                    .queryParam("numOfRows", requestDto.getNumOfRows())
                    .queryParam("pageNo", requestDto.getPageNo())
                    .queryParam("resultType", requestDto.getResultType())
                    .queryParam("basDt", requestDto.getBasDt())
                    .build(false)
                    .toUriString() + "&serviceKey=" + requestDto.getServiceKey());

            log.info("Generated URI: " + uri);  // 생성된 URI 를 로그에 출력

            // WebClient 로 요청 보내기
            String responseJson = this.webClient.get()
                    .uri(uri)  // URI 를 직접 사용
                    .headers(httpHeaders -> {
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);  // Content-Type 설정
                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));  // Accept 설정
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // JSON 데이터를 StockApiResponse 객체로 변환
            StockApiResponse apiResponse = objectMapper.readValue(responseJson, StockApiResponse.class);

            // 응답 처리
            List<StockInfoResponseDto> stockDataList = apiResponse.getResponse().getBody().getItems().getItem();

            if (stockDataList == null || stockDataList.isEmpty()) {
                log.info("받아온 데이터가 없습니다.");
                return;
            }

            updateStockDataInDB(stockDataList);
            log.info("주식시세정보가 업데이트 되었습니다: " + LocalDate.now());

        } catch (URISyntaxException e) {
            log.error("URI 생성 중 오류 발생: ", e);
        } catch (Exception e) {
            log.error("JSON 응답 처리 중 오류 발생: ", e);
        }
    }

    // 받아온 데이터를 StockPriceInfo 로 변환 후 DB에 저장 또는 업데이트
    private void updateStockDataInDB(List<StockInfoResponseDto> stockDataList) {
        for (StockInfoResponseDto stock : stockDataList) {
            StockPriceInfo stockPriceInfo = convertToStockPriceInfo(stock); // StockPriceInfo 객체로 변환
            if (stockInfoMapper.exists(stockPriceInfo.getStockCode())) {
                stockInfoMapper.updateStock(stockPriceInfo);
            } else {
                stockInfoMapper.insertStock(stockPriceInfo);
            }
        }
    }

    // StockInfoResponseDto -> StockPriceInfo 변환
    private StockPriceInfo convertToStockPriceInfo(StockInfoResponseDto stock) {
        return StockPriceInfo.builder()
                .baseDt(stock.getBasDt())
                .stockCode(stock.getSrtnCd())
                .stockName(stock.getItmsNm())
                .stockMarket(stock.getMrktCtg())
                .endPrice(stock.getClpr())
                .tradeQuantity(stock.getTrqu())
                .build();
    }
}
