package com.kb.mydata.entity.serializable;

import java.io.Serializable;
import java.util.Objects;

public class UserFundId implements Serializable {
    private String fundCode;
    private int userId;

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFundId that = (UserFundId) o;
        return userId == that.userId && Objects.equals(fundCode, that.fundCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fundCode, userId);
    }

}