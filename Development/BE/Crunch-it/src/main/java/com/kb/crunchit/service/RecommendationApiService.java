package com.kb.crunchit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.crunchit.dto.request.RecommendationApiRequestDto;
import com.kb.crunchit.dto.response.recommendation.api.*;
import com.kb.crunchit.entity.*;
import com.kb.crunchit.mapper.recommendation.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:properties/api.properties")
@Transactional
public class RecommendationApiService {

    private final WebClient webClient;
    private final RecommendationApiBondMapper bondMapper;
    private final RecommendationApiDepositMapper depositMapper;
    private final RecommendationApiDepositOptionMapper depositOptionMapper;
    private final RecommendationApiSavingMapper savingMapper;
    private final RecommendationApiSavingOptionMapper savingOptionMapper;
    private final RecommendationApiFundMapper fundMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 서비스 키를 api.properties 파일에서 주입받음
    @Value("${api.serviceKey.deposit}")
    private String depositServiceKey;

    @Value("${api.serviceKey.saving}")
    private String savingServiceKey;

    @Value("${api.serviceKey.fund}")
    private String fundServiceKey;

    @Value("${api.serviceKey.bond}")
    private String bondServiceKey;

    // 매일 오전 12시에 API 호출하여 DB 업데이트
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")  // 매일 00:00 실행
    public void fetchAndUpdateAllProductData() {
        String beginBaseDt = "20240101";
        fetchAndUpdateDepositData(beginBaseDt);
        fetchAndUpdateSavingData(beginBaseDt);
        fetchAndUpdateFundData(beginBaseDt);
        fetchAndUpdateBondData(beginBaseDt);
    }




    public void fetchAndUpdateDepositData(String beginBasDt) {
        RecommendationApiRequestDto requestDto = new RecommendationApiRequestDto(depositServiceKey, "1", "json", beginBasDt, "deposit");
        String responseJson = fetchProductData(requestDto, "deposit");

        if (responseJson != null) {
            try {
                // JSON 응답을 RecommendationDepositResponse로 파싱
                RecommendationDepositResponse depositResponse = objectMapper.readValue(responseJson, RecommendationDepositResponse.class);

                // 예금 기본 정보 (baseList) 처리
                List<RecommendationDepositResponseDto> baseList = depositResponse.getResult().getBaseList();
                if (baseList != null && !baseList.isEmpty()) {
                    updateDepositBaseData(baseList); // baseList 데이터를 처리하는 메서드 호출
                } else {
                    log.info("예금 기본 정보가 존재하지 않음");
                }

                // 예금 옵션 정보 (optionList) 처리
                List<RecommendationDepositOptionResponseDto> optionList = depositResponse.getResult().getOptionList();
                if (optionList != null && !optionList.isEmpty()) {
                    updateDepositOptionData(optionList); // optionList 데이터를 처리하는 메서드 호출
                } else {
                    log.info("예금 옵션 정보가 존재하지 않음");
                }

            } catch (Exception e) {
                log.error("예금 데이터 처리 중 오류 발생: ", e);
            }
        }
    }

    // 예금 기본 정보 (baseList)를 처리하는 메서드
    private void updateDepositBaseData(List<RecommendationDepositResponseDto> baseList) {
        for (RecommendationDepositResponseDto deposit : baseList) {
            DepositInfo depositInfo = convertToDepositInfo(deposit);
            log.info("예금 기본 정보 업데이트 대상 데이터: {}", depositInfo);

            try {
                if (depositMapper.exists(depositInfo.getProductCode()) > 0) {
                    log.info("예금 상품 업데이트 중: {}", depositInfo);
                    depositMapper.updateDeposit(depositInfo);
                } else {
                    log.info("예금 상품 삽입 중: {}", depositInfo);
                    depositMapper.insertDeposit(depositInfo);
                }
            } catch (Exception e) {
                log.error("예금 기본 정보 처리 중 오류 발생", e);
            }
        }
    }

    private DepositInfo convertToDepositInfo(RecommendationDepositResponseDto dto) {
        DepositInfo depositInfo = new DepositInfo();
        depositInfo.setProductCode(dto.getFinPrdtCd());
        depositInfo.setBankName(dto.getKorCoNm());
        depositInfo.setProductName(dto.getFinPrdtNm());
        depositInfo.setJoinMethod(dto.getJoinWay());
        return depositInfo;
    }

    // 예금 옵션 정보 (optionList)를 처리하는 메서드
    private void updateDepositOptionData(List<RecommendationDepositOptionResponseDto> optionList) {
        for (RecommendationDepositOptionResponseDto option : optionList) {
            DepositOptionInfo depositOptionInfo = convertToDepositOptionInfo(option);
            log.info("예금 옵션 업데이트 대상 데이터: {}", depositOptionInfo);

            try {
                if (depositOptionMapper.existsDepositOption(
                        depositOptionInfo.getProductCode(),
                        depositOptionInfo.getRateTypeName(),
                        depositOptionInfo.getSaveTerm()) > 0) {
                    log.info("예금 옵션 업데이트 중: {}", depositOptionInfo);
                    depositOptionMapper.updateDepositOption(depositOptionInfo);
                } else {
                    log.info("예금 옵션 상품 삽입 중: {}", depositOptionInfo);
                    depositOptionMapper.insertDepositOption(depositOptionInfo);
                }
            } catch (Exception e) {
                log.error("예금 옵션 정보 처리 중 오류 발생", e);
            }
        }
    }


    private DepositOptionInfo convertToDepositOptionInfo(RecommendationDepositOptionResponseDto dto) {
        DepositOptionInfo depositOptionInfo = new DepositOptionInfo();
        depositOptionInfo.setProductCode(dto.getFinPrdtCd());
        depositOptionInfo.setRateTypeName(dto.getIntrRateTypeNm());
        depositOptionInfo.setSaveTerm(Integer.parseInt(dto.getSaveTrm()));
        depositOptionInfo.setBaseRate(BigDecimal.valueOf(dto.getIntrRate()));
        depositOptionInfo.setMaxRate(BigDecimal.valueOf(dto.getIntrRate2()));
        return depositOptionInfo;
    }

    // 적금 데이터 업데이트 메서드
    public void fetchAndUpdateSavingData(String beginBasDt) {
        RecommendationApiRequestDto requestDto = new RecommendationApiRequestDto(savingServiceKey, "1", "json", beginBasDt, "saving");
        String responseJson = fetchProductData(requestDto, "saving");

        if (responseJson != null) {
            try {
                RecommendationSavingResponse savingResponse = objectMapper.readValue(responseJson, RecommendationSavingResponse.class);

                List<RecommendationSavingResponseDto> baseList = savingResponse.getResult().getBaseList();
                if (baseList != null && !baseList.isEmpty()) {
                    updateSavingBaseData(baseList);
                } else {
                    log.info("적금 기본 정보가 존재하지 않음");
                }

                List<RecommendationSavingOptionResponseDto> optionList = savingResponse.getResult().getOptionList();
                if (optionList != null && !optionList.isEmpty()) {
                    updateSavingOptionData(optionList);
                } else {
                    log.info("적금 옵션 정보가 존재하지 않음");
                }

            } catch (Exception e) {
                log.error("적금 데이터 처리 중 오류 발생: ", e);
            }
        }
    }

    // 적금 기본 정보 업데이트
    private void updateSavingBaseData(List<RecommendationSavingResponseDto> baseList) {
        for (RecommendationSavingResponseDto saving : baseList) {
            SavingInfo savingInfo = convertToSavingInfo(saving);
            log.info("적금 기본 정보 업데이트 대상 데이터: {}", savingInfo);

            try {
                if (savingMapper.existsSaving(savingInfo.getProductCode()) > 0) {
                    log.info("적금 상품 업데이트 중: {}", savingInfo);
                    savingMapper.updateSaving(savingInfo);
                } else {
                    log.info("적금 상품 삽입 중: {}", savingInfo);
                    savingMapper.insertSaving(savingInfo);
                }
            } catch (Exception e) {
                log.error("적금 기본 정보 처리 중 오류 발생", e);
            }
        }
    }

    private void updateSavingOptionData(List<RecommendationSavingOptionResponseDto> optionList) {
        for (RecommendationSavingOptionResponseDto option : optionList) {
            SavingOptionInfo savingOptionInfo = convertToSavingOptionInfo(option);
            log.info("적금 옵션 업데이트 대상 데이터: {}", savingOptionInfo);

            try {
                // Saving 테이블에 fin_prdt_cd 존재 여부 확인
                if (savingMapper.existsSaving(savingOptionInfo.getProductCode()) == 0) {
                    log.error("해당 상품 코드가 기본 테이블에 없음: {}", savingOptionInfo.getProductCode());
                    continue; // 기본 데이터가 없으면 옵션 삽입을 건너뜁니다.
                }

                // 옵션 데이터 삽입 또는 업데이트
                if (savingOptionMapper.existsSavingOption(
                        savingOptionInfo.getProductCode(),
                        savingOptionInfo.getRateTypeName(),
                        savingOptionInfo.getSaveTerm()) > 0) {
                    log.info("적금 옵션 데이터 업데이트 중: {}", savingOptionInfo);
                    savingOptionMapper.updateSavingOption(savingOptionInfo);
                } else {
                    log.info("적금 옵션 데이터 삽입 중: {}", savingOptionInfo);
                    savingOptionMapper.insertSavingOption(savingOptionInfo);
                }
            } catch (Exception e) {
                log.error("적금 옵션 정보 처리 중 오류 발생", e);
            }
        }
    }

    private SavingInfo convertToSavingInfo(RecommendationSavingResponseDto dto) {
        SavingInfo savingInfo = new SavingInfo();
        savingInfo.setProductCode(dto.getFinPrdtCd());
        savingInfo.setBankName(dto.getKorCoNm());
        savingInfo.setProductName(dto.getFinPrdtNm());
        savingInfo.setJoinMethod(dto.getJoinWay());
        return savingInfo;
    }
    private SavingOptionInfo convertToSavingOptionInfo(RecommendationSavingOptionResponseDto dto) {
        SavingOptionInfo savingOptionInfo = new SavingOptionInfo();
        savingOptionInfo.setProductCode(dto.getFinPrdtCd());
        savingOptionInfo.setReserveTypeName(dto.getRsrvTypeNm());
        savingOptionInfo.setRateTypeName(dto.getIntrRateTypeNm());
        savingOptionInfo.setSaveTerm(Integer.parseInt(dto.getSaveTrm()));
        savingOptionInfo.setBaseRate(BigDecimal.valueOf(dto.getIntrRate()));
        savingOptionInfo.setMaxRate(BigDecimal.valueOf(dto.getIntrRate2()));
        return savingOptionInfo;
    }


    // 펀드 데이터 업데이트
    public void fetchAndUpdateFundData(String beginBasDt) {
        RecommendationApiRequestDto requestDto = new RecommendationApiRequestDto(fundServiceKey, "1", "json", beginBasDt, "fund");
        String responseJson = fetchProductData(requestDto, "fund");

        if (responseJson != null) {
            try {
                // JSON 응답을 RecommendationFundResponse로 파싱
                RecommendationFundResponse fundResponse = objectMapper.readValue(responseJson, RecommendationFundResponse.class);

                // 응답 헤더 체크
                RecommendationFundResponse.Header header = fundResponse.getResponse().getHeader();
                if (!"00".equals(header.getResultCode())) {
                    log.error("펀드 데이터 API 호출 실패: {}", header.getResultMsg());
                    return;
                }

                // 바디에서 펀드 리스트 추출
                List<RecommendationFundResponseDto> fundList = fundResponse.getResponse().getBody().getItems().getItem();
                if (fundList != null && !fundList.isEmpty()) {
                    updateFundData(fundList);
                } else {
                    log.info("펀드 정보가 존재하지 않음");
                }

            } catch (Exception e) {
                log.error("펀드 데이터 처리 중 오류 발생: ", e);
            }
        }
    }

    private void updateFundData(List<RecommendationFundResponseDto> fundList) {
        for (RecommendationFundResponseDto fund : fundList) {
            FundInfo fundInfo = convertToFundInfo(fund);
            log.info("펀드 정보 업데이트 대상 데이터: {}", fundInfo);

            try {
                if (fundMapper.exists(fundInfo.getFndNm()) > 0) {
                    log.info("펀드 정보 업데이트 중: {}", fundInfo);
                    fundMapper.updateFund(fundInfo);
                } else {
                    log.info("펀드 정보 삽입 중: {}", fundInfo);
                    fundMapper.insertFund(fundInfo);
                }
            } catch (Exception e) {
                log.error("펀드 정보 처리 중 오류 발생", e);
            }
        }
    }



    private FundInfo convertToFundInfo(RecommendationFundResponseDto dto) {
        FundInfo fundInfo = new FundInfo();
        fundInfo.setFndTp(dto.getFndTp());
        fundInfo.setFndNm(dto.getFndNm());
        return fundInfo;
    }




    // 채권 데이터 업데이트
    public void fetchAndUpdateBondData(String beginBasDt) {
        RecommendationApiRequestDto requestDto = new RecommendationApiRequestDto(bondServiceKey, "1", "json", beginBasDt, "bond");
        String responseJson = fetchProductData(requestDto, "bond");

        if (responseJson != null) {
            try {
                // JSON 응답을 RecommendationBondResponse로 파싱
                RecommendationBondResponse bondResponse = objectMapper.readValue(responseJson, RecommendationBondResponse.class);

                // 채권 정보 처리
                List<RecommendationBondResponseDto> bondList = bondResponse.getResponse().getBody().getItems().getItem();
                if (bondList != null && !bondList.isEmpty()) {
                    updateBondData(bondList);
                } else {
                    log.info("채권 정보가 존재하지 않음");
                }

            } catch (Exception e) {
                log.error("채권 데이터 처리 중 오류 발생: ", e);
            }
        }
    }

    // 채권 데이터를 업데이트 또는 삽입하는 메서드
    private void updateBondData(List<RecommendationBondResponseDto> bondList) {
        for (RecommendationBondResponseDto bond : bondList) {
            BondInfo bondInfo = convertToBondInfo(bond);
            log.info("채권 정보 업데이트 대상 데이터: {}", bondInfo);

            try {
                if (bondMapper.exists(bondInfo.getProductName()) > 0) { // productName을 사용하여 중복 여부 확인
                    log.info("채권 정보 업데이트 중: {}", bondInfo);
                    bondMapper.updateBond(bondInfo);
                } else {
                    log.info("채권 정보 삽입 중: {}", bondInfo);
                    bondMapper.insertBond(bondInfo);
                }
            } catch (Exception e) {
                log.error("채권 정보 처리 중 오류 발생", e);
            }
        }
    }


    private BondInfo convertToBondInfo(RecommendationBondResponseDto dto) {
        BondInfo bondInfo = new BondInfo();
        bondInfo.setIssuerName(dto.getBondIsurNm());   // 채권 발행인명
        bondInfo.setProductName(dto.getIsinCdNm());    // ISIN 코드명을 productName으로 매핑
        bondInfo.setCreditRating(dto.getFnScrsItmsKcdNm());   // 신용 등급
        bondInfo.setSurfaceRate(dto.getBondSrfcInrt());   // 표면 금리
        bondInfo.setInterestType(dto.getBondIntTcdNm());  // 이자 유형
        bondInfo.setMaturityDate(dto.getBondExprDt());   // 만기일
        bondInfo.setInterestPaymentCycle(dto.getIntPayCyclCtt());  // 이자 지급 주기
        return bondInfo;
    }

    // API 호출 및 데이터 처리
    private String fetchProductData(RecommendationApiRequestDto requestDto, String productType) {
        try {
            URI uri = createUri(requestDto);  // 상품 유형에 맞는 URI 생성
            log.info("API 요청 URI: {}", uri);

            // WebClient로 요청 보내기
            String responseJson = this.webClient.get()
                    .uri(uri)
                    .headers(httpHeaders -> {
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    })
                    .exchangeToMono(clientResponse -> {
                        HttpStatus status = clientResponse.statusCode();
                        log.info("API 응답 상태 코드: {}", status);

                        // 리디렉션 처리
                        if (status.is3xxRedirection()) {
                            String redirectUrl = clientResponse.headers().asHttpHeaders().getLocation().toString();
                            log.info("리디렉션 URI: {}", redirectUrl);
                            return webClient.get().uri(redirectUrl)
                                    .retrieve()
                                    .bodyToMono(String.class);
                        }

                        if (status.is2xxSuccessful()) {
                            return clientResponse.bodyToMono(String.class);
                        } else {
                            return clientResponse.bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        log.error("API 오류 응답 본문: {}", errorBody);
                                        return Mono.error(new RuntimeException("API 호출 실패: " + status));
                                    });
                        }
                    })
                    .doOnError(error -> log.error("API 호출 중 오류 발생: ", error))
                    .block();

            return responseJson;
        } catch (Exception e) {
            log.error("API 호출 오류: ", e);
            return null;
        }
    }

    // URI 생성 로직
    private URI createUri(RecommendationApiRequestDto requestDto) throws URISyntaxException {
        String baseUri = getBaseUri(requestDto.getProductType());
        StringBuilder uriBuilder = new StringBuilder(baseUri);

        switch (requestDto.getProductType()) {
            case "fund" -> {
                return new URI(UriComponentsBuilder.fromHttpUrl(baseUri)
                        .queryParam("resultType", "json")
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 100)
                        .queryParam("beginBasDt", requestDto.getBeginBasDt())
                        .queryParam("likeFndNm", "KB")
                        .build(false)
                        .toUriString() + "&serviceKey=" + requestDto.getServiceKey());
            }
            case "bond" -> {
                String encodedBondIsurNm = UriUtils.encode("국민은행", StandardCharsets.UTF_8); // 한글을 인코딩
                return new URI(UriComponentsBuilder.fromHttpUrl(baseUri)
                        .queryParam("resultType", "json")
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 100)
                        .queryParam("basDt", requestDto.getBeginBasDt())
                        .queryParam("bondIsurNm", encodedBondIsurNm)  // 인코딩된 값 사용
                        .build(false)
                        .toUriString() + "&serviceKey=" + requestDto.getServiceKey());  // serviceKey는 인코딩하지 않음
            }
            case "deposit", "saving" -> {
                return UriComponentsBuilder.fromHttpUrl(baseUri)
                        .queryParam("auth", requestDto.getServiceKey())  // 서비스키는 인코딩하지 않음
                        .queryParam("topFinGrpNo", "020000")
                        .queryParam("pageNo", 1)
                        .build(false)  // 인코딩을 하지 않음
                        .toUri();
            }
            default -> throw new IllegalArgumentException("Invalid product type");
        }
    }

    // 각 상품 유형에 맞는 기본 URI를 반환
    private String getBaseUri(String productType) {
        return switch (productType) {
            case "deposit" -> "http://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json";
            case "saving" -> "http://finlife.fss.or.kr/finlifeapi/savingProductsSearch.json";
            case "fund" -> "https://apis.data.go.kr/1160100/service/GetFundProductInfoService/getStandardCodeInfo";
            case "bond" -> "https://apis.data.go.kr/1160100/service/GetBondIssuInfoService/getBondBasiInfo";
            default -> throw new IllegalArgumentException("Invalid product type");
        };
    }

    // 각 상품 유형에 맞는 서비스 키를 반환
    private String getServiceKeyByProductType(String productType) {
        return switch (productType) {
            case "deposit" -> depositServiceKey;
            case "saving" -> savingServiceKey;
            case "fund" -> fundServiceKey;
            case "bond" -> bondServiceKey;
            default -> throw new IllegalArgumentException("Invalid product type");
        };
    }
}