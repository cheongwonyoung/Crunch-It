package com.kb.crunchit.dto.response.recommendation.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationBondResponseDto {

    @JsonProperty("bondIsurNm")
    private String bondIsurNm;         // 채권발행인명 (예: 국민은행)

    @JsonProperty("isinCd")
    private String isinCd;             // ISIN 코드 (예: KR3804097PA9)

    @JsonProperty("isinCdNm")
    private String isinCdNm;        // ISIN 코드명 (예: 국민은행2310하30(10-3)(콜/후))

    @JsonProperty("fnScrsItmsKcdNm")
    private String fnScrsItmsKcdNm;    // 신용 등급 (예: AAA)

    @JsonProperty("bondSrfcInrt")
    private String bondSrfcInrt;       // 표면 금리 (예: 5.86)

    @JsonProperty("bondIntTcdNm")
    private String bondIntTcdNm;       // 채권이자유형코드명 (예: 복리채)

    @JsonProperty("bondExprDt")
    private String bondExprDt;         // 채권 만기일 (예: 20271109)

    @JsonProperty("intPayCyclCtt")
    private String intPayCyclCtt;      // 이자 지급 주기 (예: 12개월)
}
