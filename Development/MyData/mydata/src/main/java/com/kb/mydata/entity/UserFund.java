package com.kb.mydata.entity;

import com.kb.mydata.entity.serializable.UserFundId;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;

@Entity
@IdClass(UserFundId.class)
@Table(name = "user_fund")
@Getter
public class UserFund {
    @Id
    @Column(name = "fund_code")
    private String fundCode;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false) // 복합 키에 포함된 userId 필드
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private MdUser user;

    @Column(name = "fund_type")
    private String fundType;

    @Column(name = "fund_name")
    private String fundName;

    @Column(name = "evaluation_amount")
    private BigInteger evaluationAmount;

    @Column(name = "investing_amount")
    private BigInteger investingAmount;

}