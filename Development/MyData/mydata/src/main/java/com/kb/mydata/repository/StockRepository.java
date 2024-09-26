package com.kb.mydata.repository;

import com.kb.mydata.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<UserStock, Integer> {
    List<UserStock> findByUser_UserId(Long userId);
}