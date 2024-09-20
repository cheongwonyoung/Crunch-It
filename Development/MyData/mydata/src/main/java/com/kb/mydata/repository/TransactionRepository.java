package com.kb.mydata.repository;

import com.kb.mydata.entity.Account;
import com.kb.mydata.entity.IncomeOutcomeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<IncomeOutcomeTransaction, Integer> {
    List<IncomeOutcomeTransaction> findByUser_UserId(Long userId);
}