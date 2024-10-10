package com.kb.crunchit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class KISStockProfitResponseDto {

    @JsonProperty("output")
    private List<StockDataDTO> output;

    @Getter
    public static class StockDataDTO {

        @JsonProperty("data_rank")
        private String dataRank;

        @JsonProperty("hts_kor_isnm")
        private String isinCdNm;

        @JsonProperty("thtr_ntin")
        private String theaterNetIncome;
    }
}