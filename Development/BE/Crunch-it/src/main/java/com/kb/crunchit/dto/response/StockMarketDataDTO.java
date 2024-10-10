package com.kb.crunchit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class StockMarketDataDTO {

    @JsonProperty("output")
    private List<StockMarketDataDTO.StockDataDTO> output;

    @Getter
    public static class StockDataDTO {

        @JsonProperty("data_rank")
        private String dataRank;

        @JsonProperty("hts_kor_isnm")
        private String stockName;

        @JsonProperty("mrkt_whol_avls_rlim")
        private String marketCapRatio;

    }
}