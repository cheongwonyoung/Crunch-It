package com.kb.crunchit.dto.response.analysis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockApiResponse {
    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        private int numOfRows;
        private int pageNo;
        private int totalCount;
        private Items items;
    }

    @Data
    public static class Items {
        private List<StockInfoResponseDto> item;
    }
}
