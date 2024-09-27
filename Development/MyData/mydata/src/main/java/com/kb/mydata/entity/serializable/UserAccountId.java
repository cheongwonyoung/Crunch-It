package com.kb.mydata.entity.serializable;

import java.io.Serializable;
import java.util.Objects;

public class UserAccountId implements Serializable {
    private int accountId;
    private int userId;

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccountId that = (UserAccountId) o;
        return accountId == that.accountId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, userId);
    }

}