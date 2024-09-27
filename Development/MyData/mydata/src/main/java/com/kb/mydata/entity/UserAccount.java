package com.kb.mydata.entity;

import com.kb.mydata.entity.serializable.UserAccountId;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;

@Entity
@IdClass(UserAccountId.class)
@Table(name = "user_account")
@Getter
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false) // 복합 키에 포함된 userId 필드
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private MdUser user;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "balance")
    private BigInteger balance;

}