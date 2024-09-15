package com.kb.mydata.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MdUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String user_name;

    // 관계 설정 (계좌, 주식, 펀드, 채권)
    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @OneToMany(mappedBy = "user")
    private List<UserStock> stocks;

    @OneToMany(mappedBy = "user")
    private List<UserFund> funds;

    @OneToMany(mappedBy = "user")
    private List<UserBond> bonds;
}