package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class Stock {
    public String stockCode;
    public int userId;
    public String stockName;
    public int averagePurchasePrice;
    public int quantity;
    public String stockMarket;
}
