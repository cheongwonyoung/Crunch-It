package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class Stock {
    public String stock_code;
    public int user_id;
    public String stock_name;
    public int average_purchase_price;
    public int quantity;
    public String stock_market;
}
