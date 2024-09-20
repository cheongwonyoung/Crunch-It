package com.kb.mydata.repository;

import com.kb.mydata.entity.UserFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundRepository extends JpaRepository<UserFund, Integer> {
    List<UserFund> findByUser_UserId(Long userId);
}