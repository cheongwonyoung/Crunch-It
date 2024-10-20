package com.kb.crunchit.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockPriceInfo {
    public int stockInfoId;
    public String baseDt; // 기준일자
    public String stockCode; // 종목코드
    public String stockName; // 종목명
    public String stockMarket; // 시장구분
    public long endPrice; // 종가
    public long tradeQuantity; // 거래량
}
