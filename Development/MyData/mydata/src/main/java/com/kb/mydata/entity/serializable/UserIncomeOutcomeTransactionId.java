package com.kb.mydata.entity.serializable;

import java.io.Serializable;
import java.util.Objects;

public class UserIncomeOutcomeTransactionId implements Serializable {
    private int transactionId;
    private int accountId;
    private int userId;

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIncomeOutcomeTransactionId that = (UserIncomeOutcomeTransactionId) o;
        return transactionId == that.transactionId &&
                accountId == that.accountId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountId, userId);
    }

    // Getters and Setters
}
