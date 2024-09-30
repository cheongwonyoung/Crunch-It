package com.kb.crunchit.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class User {
    public int user_id;
    public String nickname;
    public String password;
    public String pin_number;
    public String birth;
    public int gender;
    public String phone_number;
    public String email;
    public int md_user_id;
    public String profile_url;
    public int salary;

    public List<Account> accounts;
    public List<Stock> stocks;
    public List<Bond> bonds;
    public List<Fund> funds;
    public List<InvestHistory> investHistories;
    public AssetStatistics assetStatistics;
    public Octopus octopus;
    public List<Board> boards;
    public List<Comment> comments;
    public List<Reply> replies;


    public void addAccounts(List<Account> accounts){
        this.accounts = accounts;
    }

    public void addStocks(List<Stock> stocks){
        this.stocks = stocks;
    }

    public void addBonds(List<Bond> bonds){
        this.bonds = bonds;
    }

    public void addInvestHistories(List<InvestHistory> histories){
        this.investHistories = histories;
    }

    public void addAssetStatistics(AssetStatistics assetStatistics){
        this.assetStatistics=assetStatistics;
    }

    public void addOctopus(Octopus octopus){
        this.octopus = octopus;
    }

    public void addBoards(List<Board> boards){
        this.boards = boards;
    }
    public void addComments(List<Comment> comments){
        this.comments = comments;
    }
    public void addReplies(List<Reply> replies){
        this.replies = replies;
    }

}
