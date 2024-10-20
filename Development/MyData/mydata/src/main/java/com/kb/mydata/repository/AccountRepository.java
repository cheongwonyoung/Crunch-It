package com.kb.mydata.repository;

import com.kb.mydata.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<UserAccount, Integer> {
    List<UserAccount> findByUser_UserId(Long userId);
}