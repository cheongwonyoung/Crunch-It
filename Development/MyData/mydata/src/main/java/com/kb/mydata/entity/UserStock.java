package com.kb.mydata.entity;

import com.kb.mydata.entity.serializable.UserStockId;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@IdClass(UserStockId.class)
@Table(name = "user_stock")
@Getter
public class UserStock {
    @Id
    @Column(name = "stock_code")
    private String stockCode;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false) // 복합 키에 포함된 userId 필드
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private MdUser user;

    @Column(name = "average_purchase_price")
    private int averagePurchasePrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "stock_market")
    private String stockMarket;

    @Column(name = "stock_name")
    private String stockName;

}
