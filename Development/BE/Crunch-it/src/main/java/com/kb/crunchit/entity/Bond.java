package com.kb.crunchit.entity;

import lombok.Getter;

@Getter
public class Bond {
    public String bondCode;
    public int userId;
    public String bondName;
    public int averagePurchasePrice;
    public int quantity;
    public float interestRate;
}
