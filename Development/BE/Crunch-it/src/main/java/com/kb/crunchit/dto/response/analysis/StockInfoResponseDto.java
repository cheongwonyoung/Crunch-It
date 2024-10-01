package com.kb.crunchit.dto.response.analysis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockInfoResponseDto {
    public String basDt;       // 기준일자
    public String srtnCd;      // 종목코드
    public String isinCd;      // ISIN코드
    public String itmsNm;      // 종목명
    public String mrktCtg;     // 시장구분
    public long clpr;          // 종가
    public long vs;            // 대비
    public double fltRt;       // 등락률
    public long mkp;           // 시가
    public long hipr;          // 고가
    public long lopr;          // 저가
    public long trqu;          // 거래량
    public long trPrc;         // 거래대금
    public long lstgStCnt;     // 상장주식수
    public long mrktTotAmt;    // 시가총액
}
