package com.kb.mydata.entity;

import com.kb.mydata.entity.serializable.UserIncomeOutcomeTransactionId;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@IdClass(UserIncomeOutcomeTransactionId.class)
@Table(name = "user_income_outcome_transaction")
@Getter
public class UserIncomeOutcomeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_outcome_transaction_id")
    private int transactionId;
    @Id
    @Column(name = "account_id", insertable = false, updatable = false) // 복합 키에 포함된 userId 필드
    private int accountId;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false) // 복합 키에 포함된 userId 필드
    private int userId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private UserAccount account;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private MdUser user;

    @Column(name = "transaction_type")
    private byte transactionType;

    @Column(name = "amount")
    private int amount;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

}