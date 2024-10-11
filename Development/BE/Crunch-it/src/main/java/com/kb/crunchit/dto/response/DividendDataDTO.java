package com.kb.crunchit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class DividendDataDTO {

    @JsonProperty("output")
    private List<DividendDataDTO.StockDataDTO> output;

    @Getter
    public static class StockDataDTO {

        @JsonProperty("rank")
        private String dataRank;

        @JsonProperty("isin_name")
        private String isinName;

        @JsonProperty("per_sto_divi_amt")
        private String perStoDiviAmt;

    }
}
